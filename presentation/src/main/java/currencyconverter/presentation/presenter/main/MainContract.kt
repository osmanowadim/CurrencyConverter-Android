package currencyconverter.presentation.presenter.main

import currencyconverter.presentation.BasePresenter
import currencyconverter.presentation.BaseView
import currencyconverter.presentation.InternetDependsView

interface MainContract {

    interface View : BaseView<Presenter>, InternetDependsView {


    }

    interface Presenter : BasePresenter {


    }

}
