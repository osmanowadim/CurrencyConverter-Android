package currencyconverter.presentation.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import currencyconverter.presentation.R
import currencyconverter.presentation.model.CurrencyPresentationModel
import kotlinx.android.synthetic.main.item_change_currency.view.*

class ChangeCurrencyAdapter(
    private val currencies: List<CurrencyPresentationModel>,
    private val currentCurrencyName: String,
    private val choosePosition: (String, Boolean) -> Unit
) : RecyclerView.Adapter<ChangeCurrencyAdapter.Holder>() {

    private var selectedItem = -1
    private var previousItem = -1
    private var isItemChoosing = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_change_currency, parent, false))

    override fun getItemCount(): Int = currencies.size

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(currencies[position], position)

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currency: CurrencyPresentationModel, position: Int) = with(itemView) {
            if (currentCurrencyName == currency.id && !isItemChoosing) {
                selectedItem = adapterPosition
                previousItem = adapterPosition
            }

            item_change_currency_radioBtn.isChecked = (position == selectedItem)
            item_change_currency_value.text =
                    resources.getString(R.string.item_currency_text, currency.id, currency.currencyName)
            item_change_currency_radioBtn.setOnClickListener { doOnItemClick() }
            item_change_text_container.setOnClickListener { doOnItemClick() }
        }

        private fun doOnItemClick() {
            isItemChoosing = true
            selectedItem = adapterPosition
            choosePosition(currencies[selectedItem].id, selectedItem != previousItem)
            notifyDataSetChanged()
        }

    }

}
