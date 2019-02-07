package currencyconverter.presentation

import currencyconverter.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
class UIThread @Inject constructor() : PostExecutionThread {

    override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()

}
