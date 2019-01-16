package currencyconverter.presentation.presenter.main

import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val getAllCurrencyUseCase: SingleUseCase<Currency, Unit>,
    private val mapper: CurrencyPresentationModelMapper
) : MainContract.Presenter {

    override fun start() {
        getAllCurrencies()
    }

    override fun stop() {
        getAllCurrencyUseCase.dispose()
    }

    private fun getAllCurrencies() {
        getAllCurrencyUseCase.execute(object : DisposableSingleObserver<Currency>() {

            override fun onSuccess(t: Currency) {
            }

            override fun onError(e: Throwable) {
            }
        }, Unit)
    }

}
