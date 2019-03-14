package currencyconverter.domain.repository

import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import io.reactivex.Single

/**
 * Interface that represents a Repository for getting {@link [Currency]} related data.
 */
interface CurrencyRepository {

    fun getAllCurrencies(): Single<List<Currency>>

    fun getRatio(params: String?): Single<Ratio>

}
