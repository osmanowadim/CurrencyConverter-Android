package currencyconverter.data.entity.mapper

import currencyconverter.data.entity.RatioEntity
import currencyconverter.domain.interactor.Params
import currencyconverter.domain.model.Ratio
import javax.inject.Inject

/**
 * Mapper class used to :
 * transformFromEntity {@link [RatioEntity]} (in the data layer) to {@link [Ratio]} in the domain layer.
 * transformToEntity {@link [Ratio]} (in the domain layer) to {@link [RatioEntity]} in the data layer.
 */
class RatioEntityDataMapper @Inject constructor() {

    /**
     * Transform a {@link [RatioEntity]} into an {@link [Ratio]}.
     *
     * @param ratioEntity Object to be transformed.
     * @return {@link [Ratio]} if valid {@link [RatioEntity]}.
     */
    fun transformFromEntity(ratioEntity: RatioEntity) = with(ratioEntity) {
        Ratio(this.ratio)
    }

    /**
     * Transform a Ratio into an {@link [RatioEntity]}.
     *
     * @param params Object to be transformed.
     * @return {@link [RatioEntity]} if valid {@link [Params]}.
     */
    fun transformToEntity(params: Params?) = with(params) {
        RatioEntity((this as RatioEntity).ratio)
    }

}
