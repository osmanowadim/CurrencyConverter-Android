package currencyconverter.presentation.presenter.main

import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val getAllCurrencyUseCase: SingleUseCase<List<Currency>, Unit>,
    private val getRatioUseCase: SingleUseCase<Ratio, String?>,
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
        getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {

            override fun onSuccess(t: Ratio) {

            }

            override fun onError(e: Throwable) {

            }
        }, "USD_UAH")
    }

}
