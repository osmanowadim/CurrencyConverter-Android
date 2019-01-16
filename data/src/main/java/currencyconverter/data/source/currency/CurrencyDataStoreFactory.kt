package currencyconverter.data.source.currency

import currencyconverter.data.repository.currency.CurrencyDataStore
import javax.inject.Inject

/**
 * Create an instance of a CurrencyDataStore
 */
class CurrencyDataStoreFactory @Inject constructor(
    private val remote: CurrencyRemoteDataStore
) {

    fun retrieveRemoteDataStore(): CurrencyDataStore = remote

}
