package currencyconverter.presentation.di.modules

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import currencyconverter.data.entity.mapper.CurrencyEntityDataMapper
import currencyconverter.data.entity.mapper.RatioEntityDataMapper
import currencyconverter.data.executor.JobExecutor
import currencyconverter.data.remote.impl.CurrencyRemoteImpl
import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.data.repository.currency.CurrencyDataRepository
import currencyconverter.data.repository.currency.CurrencyDataStore
import currencyconverter.data.repository.currency.CurrencyRemote
import currencyconverter.data.source.currency.CurrencyDataStoreFactory
import currencyconverter.data.source.currency.CurrencyRemoteDataStore
import currencyconverter.domain.executor.PostExecutionThread
import currencyconverter.domain.executor.ThreadExecutor
import currencyconverter.domain.repository.CurrencyRepository
import currencyconverter.presentation.di.scopes.PerApplication
import currencyconverter.presentation.UIThread
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    @Provides
    @PerApplication
    fun provideGsonFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @PerApplication
    fun provideExecutor(): ThreadExecutor = JobExecutor()

    @Provides
    @PerApplication
    fun providePostExecutor(): PostExecutionThread = UIThread()

    @Provides
    @PerApplication
    fun provideCurrencyRepository(
        factory: CurrencyDataStoreFactory,
        mapperCurrencies: CurrencyEntityDataMapper,
        mapperRatio: RatioEntityDataMapper
    ): CurrencyRepository = CurrencyDataRepository(factory, mapperCurrencies, mapperRatio)

    @Provides
    @PerApplication
    fun provideCurrencyRemote(currencyService: CurrencyService)
            : CurrencyRemote = CurrencyRemoteImpl(currencyService)

    @Provides
    @PerApplication
    fun provideCurrencyDataStore(remote: CurrencyRemote)
            : CurrencyDataStore = CurrencyRemoteDataStore(remote)

}
