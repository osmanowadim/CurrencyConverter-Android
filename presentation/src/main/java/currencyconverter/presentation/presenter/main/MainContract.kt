package currencyconverter.presentation.presenter.main

import currencyconverter.presentation.BasePresenter
import currencyconverter.presentation.BaseView
import currencyconverter.presentation.InternetDependsView
import currencyconverter.presentation.model.CurrencyPresentationModel

interface MainContract {

    interface View : BaseView<Presenter>, InternetDependsView {

        fun successfulDownload(currencies: List<CurrencyPresentationModel>, ratio: String)

    }

    interface Presenter : BasePresenter {



    }

}
