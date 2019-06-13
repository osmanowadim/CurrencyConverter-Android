package currencyconverter.presentation.di.modules.activity

import android.content.Context
import currencyconverter.domain.interactor.currency.GetAllCurrencyUseCase
import currencyconverter.domain.interactor.currency.GetRatioUseCase
import currencyconverter.presentation.di.scopes.PerActivity
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
import currencyconverter.presentation.mapper.RatioPresentationModelMapper
import currencyconverter.presentation.presenter.main.MainContract
import currencyconverter.presentation.presenter.main.MainPresenter
import currencyconverter.presentation.ui.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
open class MainActivityModule {

    @PerActivity
    @Provides
    fun provideView(activity: MainActivity) = activity as MainContract.View

    @PerActivity
    @Provides
    fun providePresenter(
        view: MainContract.View,
        context: Context,
        getAllCurrenciesUseCase: GetAllCurrencyUseCase,
        getRatioUseCase: GetRatioUseCase,
        mapperCurrency: CurrencyPresentationModelMapper,
        mapperRatio: RatioPresentationModelMapper
    ): MainContract.Presenter {
        return MainPresenter(view, context, getAllCurrenciesUseCase, getRatioUseCase, mapperCurrency, mapperRatio)
    }

}
