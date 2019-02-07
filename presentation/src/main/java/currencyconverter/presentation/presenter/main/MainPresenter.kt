package currencyconverter.presentation.presenter.main

import android.content.Context
import android.content.Context.MODE_PRIVATE
import currencyconverter.domain.interactor.SingleUseCase
import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import currencyconverter.presentation.mapper.RatioPresentationModelMapper
import currencyconverter.presentation.model.CurrencyPresentationModel
import currencyconverter.presentation.model.RatioPresentationModel
import currencyconverter.presentation.ui.main.StateOfChooseCurrencies
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
import kotlin.math.round


class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    context: Context,
    private val getAllCurrencyUseCase: SingleUseCase<List<Currency>, Unit>,
    private val getRatioUseCase: SingleUseCase<Ratio, String?>,
    private val mapperCurrency: CurrencyPresentationModelMapper,
    private val mapperRatio: RatioPresentationModelMapper
) : MainContract.Presenter {

    private var currenciesList = listOf<CurrencyPresentationModel>()
    private var currentRatio: RatioPresentationModel? = null

    private val preferenceName = "CURRENCIES_PREFERENCES"
    private val inputCurrencyPreference = "InputCurrencyName"
    private val inputCurrencyDefault = "USD"
    private val outputCurrencyPreference = "OutputCurrencyName"
    private val outputCurrencyDefault = "UAH"
    private val sharedPreferences = context.getSharedPreferences(preferenceName, MODE_PRIVATE)

    override fun start() {
        getAllCurrencies()
    }

    override fun stop() {
        getAllCurrencyUseCase.dispose()
        getRatioUseCase.dispose()
    }

    override fun getInputCurrencyName(): String {
        return sharedPreferences.getString(inputCurrencyPreference, inputCurrencyDefault)?.let { it }
            ?: inputCurrencyDefault
    }

    override fun getOutputCurrencyName(): String {
        return sharedPreferences.getString(outputCurrencyPreference, outputCurrencyDefault)?.let { it }
            ?: outputCurrencyDefault
    }

    override fun inputValueChanged(value: String) {
        view.changeOutputValue(calculateOutputValue(value))
    }

    override fun swapCurrencies() {
        getRatioForNewCurrencies(
            getOutputCurrencyName(),
            getInputCurrencyName(),
            StateOfChooseCurrencies.BOTH_CURRENCIES
        )
    }

    override fun setNewInputCurrencyName(id: String) {
        getRatioForNewCurrencies(id, getOutputCurrencyName(), StateOfChooseCurrencies.INPUT_CURRENCY)
    }

    override fun setNewOutputCurrencyName(id: String) {
        getRatioForNewCurrencies(getInputCurrencyName(), id, StateOfChooseCurrencies.OUTPUT_CURRENCY)
    }

    private fun setNewCurrency(id: String, currencyState: StateOfChooseCurrencies) {
        val editor = sharedPreferences.edit()
        when (currencyState) {
            StateOfChooseCurrencies.INPUT_CURRENCY -> {
                editor.putString(inputCurrencyPreference, id)
            }
            StateOfChooseCurrencies.OUTPUT_CURRENCY -> {
                editor.putString(outputCurrencyPreference, id)
            }
            else -> {
            }
        }
        editor.apply()
    }

    private fun calculateOutputValue(value: String): String {
        return if (value.isNotEmpty() && currentRatio != null) {
            val newOutputValue = currentRatio!!.ratio.values.first() * value.toDouble()
            roundValue(newOutputValue, 2).toString()
        } else String()
    }

    private fun roundValue(value: Double, decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(value * multiplier) / multiplier
    }

    private fun showError() {
        view.hideLoading()
        view.showErrorDownload()
    }

    private fun getAllCurrencies() {
        view.showLoading()

        if (view.isInternetAvailable()) {
            getAllCurrencyUseCase.execute(object : DisposableSingleObserver<List<Currency>>() {

                override fun onSuccess(t: List<Currency>) {
                    currenciesList = t.map(mapperCurrency::transformCurrencyToPresentationModel)
                    currenciesList = currenciesList.sortedWith(compareBy { it.id })
                    getRatio()
                }

                override fun onError(e: Throwable) {
                    showError()
                }
            }, Unit)
        } else {
            view.showErrorDownload()
        }
    }

    private fun getRatio() {
        if (view.isInternetAvailable()) {
            getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {

                override fun onSuccess(t: Ratio) {
                    view.hideLoading()
                    currentRatio = mapperRatio.transformRatioToPresentationModel(t)
                    view.successfulDownloadCurrencies(currenciesList)
                    view.changeRatio(currentRatio!!.ratio.values.first())
                    view.changeOutputValue(calculateOutputValue(view.getInputValue()))
                }

                override fun onError(e: Throwable) {
                    showError()
                }
            }, getRatioRequestParams(getInputCurrencyName(), getOutputCurrencyName()))
        } else {
            showError()
        }
    }

    private fun getRatioForNewCurrencies(
        inputCurrencyId: String,
        outputCurrencyId: String,
        currencyState: StateOfChooseCurrencies
    ) {
        view.showLoading()

        if (view.isInternetAvailable()) {
            getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {

                override fun onSuccess(t: Ratio) {
                    view.hideLoading()
                    currentRatio = mapperRatio.transformRatioToPresentationModel(t)
                    view.setNewCurrencies(inputCurrencyId, outputCurrencyId)
                    view.changeRatio(currentRatio!!.ratio.values.first())
                    view.changeOutputValue(calculateOutputValue(view.getInputValue()))
                    when (currencyState) {
                        StateOfChooseCurrencies.INPUT_CURRENCY -> {
                            setNewCurrency(inputCurrencyId, StateOfChooseCurrencies.INPUT_CURRENCY)
                        }
                        StateOfChooseCurrencies.OUTPUT_CURRENCY -> {
                            setNewCurrency(outputCurrencyId, StateOfChooseCurrencies.OUTPUT_CURRENCY)
                        }
                        StateOfChooseCurrencies.BOTH_CURRENCIES -> {
                            setNewCurrency(inputCurrencyId, StateOfChooseCurrencies.INPUT_CURRENCY)
                            setNewCurrency(outputCurrencyId, StateOfChooseCurrencies.OUTPUT_CURRENCY)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    showError()
                }
            }, getRatioRequestParams(inputCurrencyId, outputCurrencyId))
        } else {
            showError()
        }
    }

    private fun getRatioRequestParams(
        inputCurrency: String,
        outputCurrency: String
    ): String {
        return inputCurrency + "_" + outputCurrency
    }

}
