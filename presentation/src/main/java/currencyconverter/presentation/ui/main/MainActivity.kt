package currencyconverter.presentation.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import currencyconverter.presentation.R
import currencyconverter.presentation.model.CurrencyPresentationModel
import currencyconverter.presentation.presenter.main.MainContract
import currencyconverter.presentation.utils.extension.snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


fun Context.mainActivityIntent() = Intent(this, MainActivity::class.java)

class MainActivity : AppCompatActivity(), HasActivityInjector, MainContract.View {

    override fun activityInjector(): AndroidInjector<Activity> = injector

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        mainPresenter.start()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mainPresenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.stop()
    }

    override fun isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    override fun showNoInternetConnection() {
        snackbar(activity_main_container, R.string.error_internet_connection)
    }

    override fun successfulDownloadCurrencies(currencies: List<CurrencyPresentationModel>) {

    }

    override fun changeRatio(ratio: Double) {
        val rateText = "1 ${mainPresenter.getInputCurrencyName()} = $ratio ${mainPresenter.getOutputCurrencyName()}"
        activity_main_tv_rate.text = rateText
    }

    private fun init() {

    }

}
