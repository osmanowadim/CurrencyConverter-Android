package currencyconverter.data.remote.impl

import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.data.repository.currency.CurrencyRemote
import javax.inject.Inject


class CurrencyRemoteImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRemote {

    override fun getAllCurrencies() = currencyService.downloadAllCurrencies()

}
