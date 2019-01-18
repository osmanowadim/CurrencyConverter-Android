package currencyconverter.presentation.di.modules.activity

import currencyconverter.domain.interactor.currency.GetAllCurrencyUseCase
import currencyconverter.domain.interactor.currency.GetRatioUseCase
import currencyconverter.domain.scopes.PerActivity
import currencyconverter.presentation.mapper.CurrencyPresentationModelMapper
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
        getAllCurrenciesUseCase: GetAllCurrencyUseCase,
        getRatioUseCase: GetRatioUseCase,
        mapper: CurrencyPresentationModelMapper
    ): MainContract.Presenter {
        return MainPresenter(view, getAllCurrenciesUseCase, getRatioUseCase, mapper)
    }

}
