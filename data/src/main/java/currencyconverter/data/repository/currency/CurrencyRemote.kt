package currencyconverter.data.repository.currency

import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.data.entity.RatioEntity
import io.reactivex.Single


interface CurrencyRemote {

    fun getAllCurrencies(): Single<List<CurrencyEntity>>

    fun getRatio(ratioRequest: String): Single<RatioEntity>

}
