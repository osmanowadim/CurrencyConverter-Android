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

/**
 * Class (Presenter in terms of Model-View-Presenter pattern) that implement {@link [MainContract.Presenter]}.
 */
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

    /**
     * Get input currency name.
     *
     * @return input currency name {@link [String]}.
     */
    override fun getInputCurrencyName() =
        sharedPreferences.getString(inputCurrencyPreference, inputCurrencyDefault)?.let { it }
            ?: inputCurrencyDefault

    /**
     * Get output currency name.
     *
     * @return output currency name {@link [String]}.
     */
    override fun getOutputCurrencyName() =
        sharedPreferences.getString(outputCurrencyPreference, outputCurrencyDefault)?.let { it }
            ?: outputCurrencyDefault

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
                println("Wrong currencyState")
            }
        }
        editor.apply()
    }

    /**
     * Calculate output value using {@link [roundValue]}.
     *
     * @param value Object to be calculated.
     * @return {@link [String]} if valid input value
     * else
     * @return empty {@link [String]}
     */
    private fun calculateOutputValue(value: String): String {
        return if (value.isNotEmpty() && currentRatio != null) {
            val newOutputValue = currentRatio!!.ratio.values.first() * value.toDouble()
            roundValue(newOutputValue, 2).toString()
        } else String()
    }

    /**
     * Round value.
     *
     * @param value Object to be rounded.
     *
     * @param decimals number of digits after comma.
     *
     * @return {@link [Double]} rounded value
     */
    private fun roundValue(value: Double, decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(value * multiplier) / multiplier
    }

    private fun showError() {
        view.apply {
            hideLoading()
            showErrorDownload()
        }
    }

    private fun getAllCurrencies() {
        view.apply {
            showLoading()
            if (!isInternetAvailable()) {
                showErrorDownload()
                return@getAllCurrencies
            }
        }

        getAllCurrencyUseCase.execute(object : DisposableSingleObserver<List<Currency>>() {
            override fun onSuccess(t: List<Currency>) {
                currenciesList = t.map(mapperCurrency::transformCurrencyToPresentationModel).sortedWith(compareBy { it.id })
                getRatio()
            }

            override fun onError(e: Throwable) {
                showError()
            }
        }, Unit)
    }

    private fun getRatio() {
        if (!view.isInternetAvailable()) {
            showError()
            return
        }

        getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {
            override fun onSuccess(t: Ratio) {
                currentRatio = mapperRatio.transformRatioToPresentationModel(t)
                view.apply {
                    hideLoading()
                    successfulDownloadCurrencies(currenciesList)
                    changeRatio(currentRatio?.ratio?.values?.first() ?: 0.0)
                    changeOutputValue(calculateOutputValue(getInputValue()))
                }
            }

            override fun onError(e: Throwable) {
                showError()
            }
        }, getRatioRequestParams(getInputCurrencyName(), getOutputCurrencyName()))
    }

    private fun getRatioForNewCurrencies(
        inputCurrencyId: String,
        outputCurrencyId: String,
        currencyState: StateOfChooseCurrencies
    ) {
        view.apply {
            showLoading()
            if (!isInternetAvailable()) {
                showError()
            }
        }
        getRatioUseCase.execute(object : DisposableSingleObserver<Ratio>() {
            override fun onSuccess(t: Ratio) {
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

                currentRatio = mapperRatio.transformRatioToPresentationModel(t)
                view.apply {
                    hideLoading()
                    setNewCurrencies(inputCurrencyId, outputCurrencyId)
                    changeRatio(currentRatio?.ratio?.values?.first() ?: 0.0)
                    changeOutputValue(calculateOutputValue(getInputValue()))
                }
            }

            override fun onError(e: Throwable) {
                showError()
            }
        }, getRatioRequestParams(inputCurrencyId, outputCurrencyId))
    }

    private fun getRatioRequestParams(
        inputCurrency: String,
        outputCurrency: String
    ): String {
        return inputCurrency + "_" + outputCurrency
    }

}
