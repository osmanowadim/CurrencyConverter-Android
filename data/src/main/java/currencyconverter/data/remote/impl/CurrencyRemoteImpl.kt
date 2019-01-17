package currencyconverter.data.remote.impl

import android.util.Log
import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.data.remote.services.CurrencyService
import currencyconverter.data.repository.currency.CurrencyRemote
import io.reactivex.Single
import javax.inject.Inject


class CurrencyRemoteImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRemote {

    override fun getAllCurrencies(): Single<List<CurrencyEntity>> {
        return currencyService.downloadAllCurrencies()
            .flatMap { map ->
                val list = map["results"]
                if (list.isNullOrEmpty()) {
                    Log.d("myLogs", "Error")
                    return@flatMap Single.create<List<CurrencyEntity>> { it.onError(Throwable("Empty results")) }
                }
                Log.d("myLogs", "Success = $list")
                return@flatMap Single.create<List<CurrencyEntity>> { it.onSuccess(list) }
            }
    }

}
