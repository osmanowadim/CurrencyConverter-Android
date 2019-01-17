package currencyconverter.presentation.presenter.main

import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val getAllCurrencyUseCase: SingleUseCase<List<Currency>, Unit>,
    private val mapper: CurrencyPresentationModelMapper
) : MainContract.Presenter {

    private var currenciesList = mutableListOf<Currency>()

    override fun start() {
        getAllCurrencies()
    }

    override fun stop() {
        getAllCurrencyUseCase.dispose()
    }

    private fun getAllCurrencies() {
        getAllCurrencyUseCase.execute(object : DisposableSingleObserver<List<Currency>>() {

            override fun onSuccess(t: List<Currency>) {
                currenciesList = t.toMutableList()
                getRatio()
            }

            override fun onError(e: Throwable) {
            }
        }, Unit)
    }

    private fun getRatio() {

    }

}
