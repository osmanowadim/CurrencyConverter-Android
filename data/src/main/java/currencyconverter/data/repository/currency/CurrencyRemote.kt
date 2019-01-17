package currencyconverter.data.repository.currency

import currencyconverter.data.entity.CurrencyEntity
import io.reactivex.Single


interface CurrencyRemote {

    fun getAllCurrencies(): Single<List<CurrencyEntity>>

}
