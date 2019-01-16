package currencyconverter.data.remote.services

import currencyconverter.data.entity.CurrencyEntity
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    fun downloadAllCurrencies(): Single<CurrencyEntity>

}
