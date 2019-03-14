package currencyconverter.presentation.presenter.splash

import javax.inject.Inject

/**
 * Class (Presenter in terms of Model-View-Presenter pattern) that implement {@link [SplashContract.Presenter]}.
 */
class SplashPresenter @Inject constructor(
    private val view: SplashContract.View
) : SplashContract.Presenter {


    override fun start() {
        view.navigateToMain()
    }

    override fun stop() {
    }

}
