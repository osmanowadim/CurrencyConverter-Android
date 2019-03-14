package currencyconverter.domain.interactor.currency

import currencyconverter.domain.executor.PostExecutionThread
import currencyconverter.domain.executor.ThreadExecutor
import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Ratio
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Class for a Use Case (Interactor in terms of Clean Architecture) that implement {@link [SingleUseCase]}.
 *
 * By convention each [SingleUseCase] implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
class GetRatioUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val currencyRepository: CurrencyRepository
) : SingleUseCase<Ratio, String?>(threadExecutor, postExecutionThread) {

    override fun buildUseCase(params: String?): Single<Ratio> = currencyRepository.getRatio(params)

}
