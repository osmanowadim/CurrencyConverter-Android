package currencyconverter.presentation.presenter.main

import currencyconverter.presentation.BasePresenter
import currencyconverter.presentation.BaseView
import currencyconverter.presentation.InternetDependsView
import currencyconverter.presentation.model.CurrencyPresentationModel

interface MainContract {

    interface View : BaseView<Presenter>, InternetDependsView {

        fun successfulDownloadCurrencies(currencies: List<CurrencyPresentationModel>)

        fun changeRatio(ratio: Double)

        fun changeOutputValue(value: String)

        fun setNewCurrencies(inputCurrencyId: String, outputCurrencyId: String)

        fun getInputValue(): String

        fun showLoading()

        fun hideLoading()

        fun showErrorDownload()

    }

    interface Presenter : BasePresenter {

        fun getInputCurrencyName(): String

        fun getOutputCurrencyName(): String

        fun setNewInputCurrencyName(id: String)

        fun setNewOutputCurrencyName(id: String)

        fun inputValueChanged(value: String)

        fun swapCurrencies()

    }

}
