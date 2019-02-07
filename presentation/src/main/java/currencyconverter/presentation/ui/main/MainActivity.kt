package currencyconverter.presentation.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import currencyconverter.presentation.R
import currencyconverter.presentation.model.CurrencyPresentationModel
import currencyconverter.presentation.presenter.main.MainContract
import currencyconverter.presentation.ui.about.aboutActivityIntent
import currencyconverter.presentation.utils.extension.animateChangingActivityFade
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

    private var currencies: List<CurrencyPresentationModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPresenter.start()
        init()
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

    override fun showErrorDownload() {
        snackbar(activity_main_container, R.string.error_loading_currencies)
    }

    override fun successfulDownloadCurrencies(currencies: List<CurrencyPresentationModel>) {
        this.currencies = currencies
    }

    override fun changeRatio(ratio: Double) {
        val rateText = "1 ${mainPresenter.getInputCurrencyName()} = $ratio ${mainPresenter.getOutputCurrencyName()}"
        activity_main_tv_rate.text = rateText
    }

    override fun changeOutputValue(value: String) {
        activity_main_et_output.text = value
    }

    override fun setNewCurrencies(inputCurrencyId: String, outputCurrencyId: String) {
        activity_main_tv_input.text = inputCurrencyId
        activity_main_tv_output.text = outputCurrencyId
    }

    override fun showLoading() {
        changeLoadingVisibility(false)
    }

    override fun hideLoading() {
        changeLoadingVisibility(false)
    }

    override fun getInputValue() = activity_main_et_input.text.toString()

    private fun changeLoadingVisibility(mustShowing: Boolean) {
        if (mustShowing) activity_main_progress.visibility = View.GONE
        else activity_main_progress.visibility = View.VISIBLE
        activity_main_tv_input.isClickable = mustShowing
        activity_main_iv_swap.isClickable = mustShowing
        activity_main_tv_output.isClickable = mustShowing
        activity_main_btn_about.isClickable = mustShowing
    }

    private fun init() {
        activity_main_tv_input.text = mainPresenter.getInputCurrencyName()
        activity_main_tv_output.text = mainPresenter.getOutputCurrencyName()
        activity_main_btn_about.setOnClickListener {
            startActivity(aboutActivityIntent())
            animateChangingActivityFade()
        }
        activity_main_iv_swap.setOnClickListener {
            mainPresenter.swapCurrencies()
        }
        activity_main_tv_input.setOnClickListener {
            currencies?.let { showDialogChooseCurrency(StateOfChooseCurrencies.INPUT_CURRENCY, it) }
        }
        activity_main_tv_output.setOnClickListener {
            currencies?.let { showDialogChooseCurrency(StateOfChooseCurrencies.OUTPUT_CURRENCY, it) }
        }
        activity_main_et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mainPresenter.inputValueChanged(activity_main_et_input.text.toString())
            }
        })
    }

    private fun showDialogChooseCurrency(
        currencyState: StateOfChooseCurrencies,
        currenciesList: List<CurrencyPresentationModel>
    ) {
        val currentCurrencyName =
            if (currencyState == StateOfChooseCurrencies.INPUT_CURRENCY) mainPresenter.getInputCurrencyName()
            else mainPresenter.getOutputCurrencyName()

        val currencyDialog = CurrencyDialog(this, currenciesList, currentCurrencyName, currencyState) { id ->
            if (currencyState == StateOfChooseCurrencies.INPUT_CURRENCY) mainPresenter.setNewInputCurrencyName(id)
            else mainPresenter.setNewOutputCurrencyName(id)
        }
        currencyDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            currencyDialog.show()
        }
    }

}
