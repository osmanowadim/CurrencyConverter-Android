package currencyconverter.presentation.di.modules.activity

import currencyconverter.domain.scopes.PerActivity
import dagger.Module
import dagger.Provides
import currencyconverter.presentation.presenter.splash.SplashContract
import currencyconverter.presentation.presenter.splash.SplashPresenter
import currencyconverter.presentation.ui.splash.SplashActivity

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
