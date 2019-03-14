package currencyconverter.data.remote

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * ServiceProvider class used to create {@link [Retrofit]} client
 */
class ServiceProvider @Inject constructor() {

    // Set timeout for connecting and reading from the network
    private val timeout = 45L

    /**
     * Get an implementation of the API endpoints.
     *
     * @param serviceClass - defined interface of the API endpoints.
     * @return {<S>} implementation of the API endpoints.
     */
    fun <S> provide(serviceClass: Class<S>): S =
        createRetrofit(createClient()).create(serviceClass)

    /**
     * Create the {@link [Retrofit]} instance using the configured values.
     *
     * @param client - {@link [OkHttpClient]}
     * @return the {@link [Retrofit]} instance using the configured values.
     */
    private fun createRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(client)
        .baseUrl("https://free.currencyconverterapi.com/api/v6/")
        .build()

    /**
     * Create the {@link [OkHttpClient]} instance using the configured values.
     *
     * @return the {@link [OkHttpClient]} instance using the configured values.
     */
    private fun createClient() = okhttp3.OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .build()

}
