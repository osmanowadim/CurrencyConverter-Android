package currencyconverter.data.source.currency

import currencyconverter.data.repository.currency.CurrencyDataStore
import currencyconverter.data.repository.currency.CurrencyRemote
import javax.inject.Inject


class CurrencyRemoteDataStore @Inject constructor(
    private val remote: CurrencyRemote
) : CurrencyDataStore {

    override fun getAllCurrencies() = remote.getAllCurrencies()

}
