package currencyconverter.presentation.di.modules

import currencyconverter.data.remote.ServiceProvider
import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.presentation.di.scopes.PerApplication
import dagger.Module
import dagger.Provides


@Module
class ApiModule {

    @Provides
    @PerApplication
    fun provideCurrencyService(serviceProvider: ServiceProvider)
            : CurrencyService = serviceProvider.provide(CurrencyService::class.java)

}
