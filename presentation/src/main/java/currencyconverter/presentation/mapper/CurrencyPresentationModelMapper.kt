package currencyconverter.presentation.mapper

import currencyconverter.domain.model.Currency
import currencyconverter.presentation.model.CurrencyPresentationModel
import javax.inject.Inject


class CurrencyPresentationModelMapper @Inject constructor() {

    fun transformCurrencyToPresentationModel(currency: Currency) = with(currency) {
        CurrencyPresentationModel(this.currencyName, this.currencySymbol, this.id)
    }

    fun transformPresentationModelToCurrency(currencyPresentationModel: CurrencyPresentationModel) =
        with(currencyPresentationModel) {
            Currency(this.currencyName, this.currencySymbol, this.id)
        }

}
