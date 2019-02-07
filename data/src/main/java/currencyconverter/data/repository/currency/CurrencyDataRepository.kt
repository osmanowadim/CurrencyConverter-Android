package currencyconverter.data.repository.currency


import currencyconverter.data.entity.mapper.CurrencyEntityDataMapper
import currencyconverter.data.entity.mapper.RatioEntityDataMapper
import currencyconverter.data.source.currency.CurrencyDataStoreFactory
import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject


class CurrencyDataRepository @Inject constructor(
    private val factory: CurrencyDataStoreFactory,
    private val mapperCurrencies: CurrencyEntityDataMapper,
    private val mapperRatio: RatioEntityDataMapper
) : CurrencyRepository {

    override fun getAllCurrencies(): Single<List<Currency>> {
        return factory.retrieveRemoteDataStore()
            .getAllCurrencies()
            .flatMap { currenciesList ->
                return@flatMap Single.create<List<Currency>> { it.onSuccess(currenciesList.map(mapperCurrencies::transformFromEntity)) }
            }
    }

    override fun getRatio(params: String?): Single<Ratio> {
        if (params.isNullOrBlank()) {
            return Single.create<Ratio> { it.onError(Throwable("Empty request params")) }
        }
        return factory.retrieveRemoteDataStore()
            .getRatio(params)
            .flatMap { entity ->
                return@flatMap Single.create<Ratio> { it.onSuccess(mapperRatio.transformFromEntity(entity)) }
            }

    }

}
