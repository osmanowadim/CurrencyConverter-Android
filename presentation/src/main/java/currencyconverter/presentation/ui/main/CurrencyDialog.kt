package currencyconverter.presentation.ui.main

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import currencyconverter.presentation.R
import currencyconverter.presentation.model.CurrencyPresentationModel
import kotlinx.android.synthetic.main.dialog_change_currency.*

class CurrencyDialog(
    activity: Activity,
    private val currencies: List<CurrencyPresentationModel>,
    private val currentCurrencyName: String,
    private val stateOfChooseCurrencies: StateOfChooseCurrencies,
    private val currencyUpdateListener: (String) -> Unit
) : Dialog(activity) {

    private var isCurrencyChanged = false
    private var newCurrencyId = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_change_currency)
        initialization()
    }

    private fun initialization() {
        if (stateOfChooseCurrencies == StateOfChooseCurrencies.INPUT_CURRENCY) {
            dialog_change_currency_tv.text = context.getString(R.string.dialog_change_input_currency_title)
        } else {
            dialog_change_currency_tv.text = context.getString(R.string.dialog_change_output_currency_title)
        }
        dialog_change_currency_recyclerView.layoutManager = LinearLayoutManager(context)
        dialog_change_currency_recyclerView.adapter =
                ChangeCurrencyAdapter(currencies, currentCurrencyName) { newId, isChanged ->
                    isCurrencyChanged = isChanged
                    newCurrencyId = newId
                }
        dialog_change_currency_recyclerView.scrollToPosition(currencies.indexOfFirst { it.id == currentCurrencyName })
        dialog_change_currency_btn_ok.setOnClickListener {
            if (isCurrencyChanged) currencyUpdateListener(newCurrencyId)
            closeDialog()
        }
        dialog_change_currency_btn_cancel.setOnClickListener {
            closeDialog()
        }
    }

    private fun closeDialog() {
        dismiss()
    }

}
