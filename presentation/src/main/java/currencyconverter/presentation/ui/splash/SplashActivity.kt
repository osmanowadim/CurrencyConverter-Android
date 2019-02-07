package currencyconverter.presentation.ui.splash

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import currencyconverter.presentation.R
import currencyconverter.presentation.presenter.splash.SplashContract
import currencyconverter.presentation.ui.main.mainActivityIntent
import currencyconverter.presentation.utils.extension.animateChangingActivityFade
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), HasActivityInjector, SplashContract.View {

    override fun activityInjector(): AndroidInjector<Activity> = injector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var splashPresenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashPresenter.start()
    }

    override fun setPresenter(presenter: SplashContract.Presenter) {
        splashPresenter = presenter
    }

    override fun navigateToMain() {
        startActivity(mainActivityIntent())
        animateChangingActivityFade()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashPresenter.stop()
    }

}
