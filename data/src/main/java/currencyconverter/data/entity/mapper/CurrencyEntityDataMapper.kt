package currencyconverter.data.entity.mapper

import currencyconverter.data.entity.CurrencyEntity
import currencyconverter.domain.interactor.Params
import currencyconverter.domain.model.Currency
import currencyconverter.domain.scopes.PerApplication
import javax.inject.Inject

/**
 * Mapper class used to :
 * transformFromEntity {@link CurrencyEntity} (in the data layer) to {@link Currency} in the domain layer.
 * transformToEntity {@link Currency} (in the domain layer) to {@link CurrencyEntity} in the data layer.
 */
@PerApplication
class CurrencyEntityDataMapper @Inject constructor() {

    /**
     * Transform a {@link CurrencyEntity} into an {@link Currency}.
     *
     * @param currencyEntity Object to be transformed.
     * @return {@link Currency} if valid {@link CurrencyEntity}.
     */
    fun transformFromEntity(currencyEntity: CurrencyEntity) = with(currencyEntity) {
        Currency(this.currencyName, this.currencySymbol, this.id)
    }

    /**
     * Transform a Currency into an {@link CurrencyEntity}.
     *
     * @param params Object to be transformed.
     * @return {@link CurrencyEntity} if valid {@link Params}.
     */
    fun transformToEntity(params: Params?) = with(params){
        CurrencyEntity((this as CurrencyEntity).currencyName, this.currencySymbol, this.id)
    }

}
