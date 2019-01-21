package currencyconverter.presentation.presenter.main

import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import currencyconverter.presentation.mapper.RatioPresentationModelMapper
import currencyconverter.presentation.model.CurrencyPresentationModel
import currencyconverter.presentation.model.RatioPresentationModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val getAllCurrencyUseCase: SingleUseCase<List<Currency>, Unit>,
    private val getRatioUseCase: SingleUseCase<Ratio, String?>,
    private val mapperCurrency: CurrencyPresentationModelMapper,
    private val mapperRatio: RatioPresentationModelMapper
) : MainContract.Presenter {

    private var currenciesList = listOf<CurrencyPresentationModel>()
    private lateinit var currentRatio: RatioPresentationModel

    override fun start() {
        getAllCurrencies()
    }

    override fun stop() {
        getAllCurrencyUseCase.dispose()
    }

    override fun getCurrencies() = currenciesList

    override fun getInputCurrencyName(): String {
        return "USD"
    }

    override fun getOutputCurrencyName(): String {
        return "UAH"
    }

    private fun getAllCurrencies() {
        getAllCurrencyUseCase.execute(object : DisposableSingleObserver<List<Currency>>() {

            override fun onSuccess(t: List<Currency>) {
                currenciesList = t.map(mapperCurrency::transformCurrencyToPresentationModel)
                getRatio()
            }

            override fun onError(e: Throwable) {
            }
        }, Unit)
    }

    private fun getRatio() {
        getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {

            override fun onSuccess(t: Ratio) {
                currentRatio = mapperRatio.transformRatioToPresentationModel(t)
                view.changeRatio(t.ratio.values.first())
            }

            override fun onError(e: Throwable) {

            }
        }, getRatioRequestParams(currenciesList[1], currenciesList[2]))
    }

    private fun getRatioRequestParams(
        inputCurrency: CurrencyPresentationModel,
        outputCurrency: CurrencyPresentationModel
    ): String {
        return inputCurrency.id + "_" + outputCurrency.id
    }

}
