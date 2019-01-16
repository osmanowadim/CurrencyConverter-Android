package currencyconverter.presentation.presenter.splash

import currencyconverter.presentation.BasePresenter
import currencyconverter.presentation.BaseView

interface SplashContract {

    interface View : BaseView<Presenter> {

        fun navigateToMain()

    }

    interface Presenter : BasePresenter {

    }

}
