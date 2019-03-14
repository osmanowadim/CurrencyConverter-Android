package currencyconverter.data.repository.currency


import currencyconverter.data.entity.mapper.CurrencyEntityDataMapper
import currencyconverter.data.entity.mapper.RatioEntityDataMapper
import currencyconverter.data.source.currency.CurrencyDataStoreFactory
import currencyconverter.domain.model.Currency
import currencyconverter.domain.model.Ratio
import currencyconverter.domain.repository.CurrencyRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * DataRepository class used to :
 * getAllCurrencies - return Single<List<{@link [Currency]}>>
 * getRatio - return Single<{@link [Ratio]}>.
 */
class CurrencyDataRepository @Inject constructor(
    private val factory: CurrencyDataStoreFactory,
    private val mapperCurrencies: CurrencyEntityDataMapper,
    private val mapperRatio: RatioEntityDataMapper
) : CurrencyRepository {

    /**
     * Get all currencies Single<List<{@link [Currency]}>>.
     *
     * @return Single<List<{@link [Currency]}>>
     */
    override fun getAllCurrencies(): Single<List<Currency>> {
        return factory.retrieveRemoteDataStore()
            .getAllCurrencies()
            .flatMap { currenciesList ->
                return@flatMap Single.create<List<Currency>> { it.onSuccess(currenciesList.map(mapperCurrencies::transformFromEntity)) }
            }
    }

    /**
     * Get ration between currencies
     *
     * @param params - request consisting of currency name
     * @return Single<{@link [Ratio]}> - currency ratio
     */
    override fun getRatio(params: String?): Single<Ratio> {
        if (params.isNullOrBlank())
            return Single.create<Ratio> { it.onError(Throwable("Empty request params")) }

        return factory.retrieveRemoteDataStore()
            .getRatio(params)
            .flatMap { entity ->
                return@flatMap Single.create<Ratio> { it.onSuccess(mapperRatio.transformFromEntity(entity)) }
            }
    }

}
