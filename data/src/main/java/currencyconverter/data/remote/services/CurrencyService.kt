package currencyconverter.data.remote.services

import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.data.entity.RatioEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("currencies")
    fun downloadAllCurrencies(): Single<Map<String, Map<String, CurrencyEntity>>>

    @GET("convert")
    fun getRatio(@Query("q") ratioRequest: String): Single<RatioEntity>

}
