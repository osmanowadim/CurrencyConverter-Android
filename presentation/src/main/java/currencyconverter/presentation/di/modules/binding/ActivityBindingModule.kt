package currencyconverter.presentation.di.modules.binding

import currencyconverter.domain.scopes.PerActivity
import currencyconverter.presentation.di.modules.activity.MainActivityModule
import currencyconverter.presentation.di.modules.activity.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import currencyconverter.presentation.ui.main.MainActivity
import currencyconverter.presentation.ui.splash.SplashActivity

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}
