package currencyconverter.data.repository.currency

import currencyconverter.data.entity.CurrencyEntity
import io.reactivex.Single


interface CurrencyDataStore {

    fun getAllCurrencies(): Single<CurrencyEntity>

}
