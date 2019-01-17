package currencyconverter.data.repository.currency


import currencyconverter.data.entity.mapper.CurrencyEntityDataMapper
import currencyconverter.data.source.currency.CurrencyDataStoreFactory
import currencyconverter.domain.model.Currency
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject


class CurrencyDataRepository @Inject constructor(
    private val factory: CurrencyDataStoreFactory,
    private val mapper: CurrencyEntityDataMapper
) : CurrencyRepository {

    override fun getAllCurrencies(): Single<List<Currency>> {
        return factory.retrieveRemoteDataStore()
            .getAllCurrencies()
            .flatMap { currenciesList ->
                return@flatMap Single.create<List<Currency>> { it.onSuccess(currenciesList.map(mapper::transformFromEntity)) }
            }
    }

}
