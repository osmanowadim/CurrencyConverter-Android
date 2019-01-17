package currencyconverter.domain.interactor.currency

import currencyconverter.domain.executor.PostExecutionThread
import currencyconverter.domain.executor.ThreadExecutor
import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject


class GetAllCurrencyUseCase @Inject constructor(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val currencyRepository: CurrencyRepository
) : SingleUseCase<List<Currency>, Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCase(params: Unit?): Single<List<Currency>> = currencyRepository.getAllCurrencies()

}
