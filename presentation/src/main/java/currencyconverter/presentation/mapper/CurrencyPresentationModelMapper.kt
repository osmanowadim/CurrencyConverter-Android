package currencyconverter.presentation.mapper

import currencyconverter.domain.model.Currency
import currencyconverter.presentation.model.CurrencyPresentationModel
import javax.inject.Inject

/**
 * Mapper class used to :
 * transformCurrencyToPresentationModel {@link [Currency]} (in the domain layer) to {@link [CurrencyPresentationModel]} in the presentation layer.
 * transformPresentationModelToCurrency {@link [CurrencyPresentationModel]} (in the presentation layer) to {@link [Currency]} in the domain layer.
 */
class CurrencyPresentationModelMapper @Inject constructor() {

    /**
     * Transform a {@link [Currency]} into an {@link [CurrencyPresentationModel]}.
     *
     * @param currency Object to be transformed.
     * @return {@link [CurrencyPresentationModel]}.
     */
    fun transformCurrencyToPresentationModel(currency: Currency) = with(currency) {
        CurrencyPresentationModel(this.currencyName, this.currencySymbol, this.id)
    }

    /**
     * Transform a {@link [CurrencyPresentationModel]} into an {@link [Currency]}.
     *
     * @param currencyPresentationModel Object to be transformed.
     * @return {@link [CurrencyPresentationModel]}.
     */
    fun transformPresentationModelToCurrency(currencyPresentationModel: CurrencyPresentationModel) =
        with(currencyPresentationModel) {
            Currency(this.currencyName, this.currencySymbol, this.id)
        }

}
