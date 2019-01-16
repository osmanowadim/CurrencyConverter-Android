package currencyconverter.data.executor

import currencyconverter.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Decorated [ThreadPoolExecutor]
 */
open class JobExecutor @Inject constructor() : ThreadExecutor {

    private val workQueue: LinkedBlockingQueue<Runnable> = LinkedBlockingQueue()

    private val threadFactory: ThreadFactory = JobThreadFactory()

    private val threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory)

    override fun execute(runnable: Runnable?) {
        if (runnable == null) throw IllegalArgumentException("Runnable to execute cannot be null")
        threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable) = Thread(runnable, THREAD_NAME + counter++)

        companion object {
            private const val THREAD_NAME = "currencyconverter_"
        }
    }

    companion object {
        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5
        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10L
        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

}
