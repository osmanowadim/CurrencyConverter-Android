package currencyconverter.data.remote

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ServiceProvider @Inject constructor() {

    // Set timeout for connecting and reading from the network
    private val timeout = 45L

    fun <S> provide(serviceClass: Class<S>): S =
            createRetrofit(createClient()).create(serviceClass)

    private fun createRetrofit(client: OkHttpClient) = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .baseUrl("https://free.currencyconverterapi.com/api/v6/")
            .build()

    private fun createClient() = okhttp3.OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()

}
