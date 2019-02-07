package currencyconverter.data.remote.impl

import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.data.entity.RatioEntity
import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.data.repository.currency.CurrencyRemote
import io.reactivex.Single
import javax.inject.Inject


class CurrencyRemoteImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRemote {

    override fun getRatio(ratioRequest: String): Single<RatioEntity> {
        return currencyService.getRatio(ratioRequest, "ultra")
            .flatMap { ratioObject ->
                return@flatMap Single.create<RatioEntity> { it.onSuccess(RatioEntity(ratioObject)) }
            }
    }

    override fun getAllCurrencies(): Single<List<CurrencyEntity>> {
        return currencyService.downloadAllCurrencies()
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
