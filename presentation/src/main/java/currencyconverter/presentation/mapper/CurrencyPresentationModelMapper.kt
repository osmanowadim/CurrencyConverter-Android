package currencyconverter.presentation.mapper

import currencyconverter.domain.model.Currency
import currencyconverter.presentation.model.CurrencyPresentationModel
import javax.inject.Inject


class CurrencyPresentationModelMapper @Inject constructor() {

    fun transformUserToModel(currency: Currency) = with(currency) {
        CurrencyPresentationModel(this.currencyName, this.currencySymbol, this.id)
    }

    fun transformModelToUser(text: CurrencyPresentationModel) = with(text) {
        Currency(this.currencyName, this.currencySymbol, this.id)
    }

}
