package currencyconverter.data.remote.services

import currencyconverter.data.entity.CurrencyEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("currencies")
    fun downloadAllCurrencies(@Query("apiKey") apiKey: String): Single<Map<String, Map<String, CurrencyEntity>>>

    @GET("convert")
    fun getRatio(@Query("q") ratioRequest: String, @Query("compact") param: String, @Query("apiKey") apiKey: String): Single<Map<String, Double>>

}
