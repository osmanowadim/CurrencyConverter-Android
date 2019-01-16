package currencyconverter.presentation.di.components

import android.app.Application
import currencyconverter.domain.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import currencyconverter.presentation.CurrencyConverterApplication
import currencyconverter.presentation.di.modules.ApiModule
import currencyconverter.presentation.di.modules.ApplicationModule
import currencyconverter.presentation.di.modules.binding.ActivityBindingModule

/**
 * A component whose lifetime is the life of the application.
 */
@PerApplication
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class,
    ApiModule::class, ActivityBindingModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: CurrencyConverterApplication)

}
