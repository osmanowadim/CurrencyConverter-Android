package currencyconverter.data.remote.impl

import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.data.entity.RatioEntity
import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.data.repository.currency.CurrencyRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of {@link [CurrencyRemote]} class used to :
 * getRatio - Single<RatioEntity> from {@link [CurrencyService]}.
 * getAllCurrencies - Single<List<CurrencyEntity>> from {@link [CurrencyService]}.
 */
class CurrencyRemoteImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRemote {

    /**
     * Get Single<{@link [RatioEntity]}> from {@link [CurrencyService]}.
     *
     * @param ratioRequest - request consisting of currency name
     * @return Single<{@link [RatioEntity]}> - currency ratio
     */
    override fun getRatio(ratioRequest: String): Single<RatioEntity> {
        return currencyService.getRatio(ratioRequest, "ultra", "4a8aa2fc7dde0228d672")
            .flatMap { ratioObject ->
                return@flatMap Single.create<RatioEntity> { it.onSuccess(RatioEntity(ratioObject)) }
            }
    }

    /**
     * Get Single<List<{@link [CurrencyEntity]}>> from {@link [CurrencyService]}.
     *
     * @return Single<List<{@link [CurrencyEntity]}>> - list of all currencies
     */
    override fun getAllCurrencies(): Single<List<CurrencyEntity>> {
        return currencyService.downloadAllCurrencies("4a8aa2fc7dde0228d672")
            .flatMap { map ->
                val resultMap = map["results"]
                if (resultMap.isNullOrEmpty()) {
                    return@flatMap Single.create<List<CurrencyEntity>> { it.onError(Throwable("Empty results")) }
                }
                val currencies = mutableListOf<CurrencyEntity>()

                for (currencyPair in resultMap) {
                    currencies.add(currencyPair.value)
                }

                return@flatMap Single.create<List<CurrencyEntity>> { it.onSuccess(currencies) }
            }
    }

}
