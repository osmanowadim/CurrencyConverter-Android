package currencyconverter.domain.interactor.currency

import currencyconverter.domain.executor.PostExecutionThread
import currencyconverter.domain.executor.ThreadExecutor
import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Ratio
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject


class GetRatioUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val currencyRepository: CurrencyRepository
) : SingleUseCase<Ratio, String?>(threadExecutor, postExecutionThread) {

    override fun buildUseCase(params: String?): Single<Ratio> = currencyRepository.getRatio(params)

}
