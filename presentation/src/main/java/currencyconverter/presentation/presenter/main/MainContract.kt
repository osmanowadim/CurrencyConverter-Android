package currencyconverter.presentation.presenter.main

import currencyconverter.presentation.BasePresenter
import currencyconverter.presentation.BaseView
import currencyconverter.presentation.InternetDependsView
import currencyconverter.presentation.model.CurrencyPresentationModel

interface MainContract {

    interface View : BaseView<Presenter>, InternetDependsView {

        fun successfulDownloadCurrencies(currencies: List<CurrencyPresentationModel>)

        fun changeRatio(ratio: Double)

    }

    interface Presenter : BasePresenter {

        fun getCurrencies(): List<CurrencyPresentationModel>

        fun getInputCurrencyName(): String

        fun getOutputCurrencyName(): String

    }

}
