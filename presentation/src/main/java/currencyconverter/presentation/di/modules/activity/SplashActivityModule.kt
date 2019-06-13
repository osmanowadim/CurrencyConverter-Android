package currencyconverter.presentation.di.modules.activity

import currencyconverter.presentation.di.scopes.PerActivity
import currencyconverter.presentation.presenter.splash.SplashContract
import currencyconverter.presentation.presenter.splash.SplashPresenter
import currencyconverter.presentation.ui.splash.SplashActivity
import dagger.Module
import dagger.Provides

@Module
open class SplashActivityModule {

    @PerActivity
    @Provides
    fun provideView(activity: SplashActivity) = activity as SplashContract.View

    @PerActivity
    @Provides
    fun providePresenter(
        view: SplashContract.View
    ): SplashContract.Presenter {
        return SplashPresenter(view)
    }

}
