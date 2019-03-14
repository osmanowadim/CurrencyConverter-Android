package currencyconverter.data.source.currency

import currencyconverter.data.entity.RatioEntity
import currencyconverter.data.repository.currency.CurrencyDataStore
import currencyconverter.data.repository.currency.CurrencyRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of {@link [CurrencyDataStore]}
 */
class CurrencyRemoteDataStore @Inject constructor(
    private val remote: CurrencyRemote
) : CurrencyDataStore {

    override fun getRatio(ratioRequest: String): Single<RatioEntity> = remote.getRatio(ratioRequest)

    override fun getAllCurrencies() = remote.getAllCurrencies()

}
