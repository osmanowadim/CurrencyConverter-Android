package currencyconverter.presentation.mapper

import currencyconverter.domain.model.Ratio
import currencyconverter.presentation.model.RatioPresentationModel
import javax.inject.Inject

/**
 * Mapper class used to :
 * transformRatioToPresentationModel {@link [Ratio]} (in the domain layer) to {@link [RatioPresentationModel]} in the presentation layer.
 * transformPresentationModelToRatio {@link [RatioPresentationModel]} (in the presentation layer) to {@link [Ratio]} in the domain layer.
 */
class RatioPresentationModelMapper @Inject constructor() {

    /**
     * Transform a {@link [Ratio]} into an {@link [RatioPresentationModel]}.
     *
     * @param ratio Object to be transformed.
     * @return {@link [RatioPresentationModel]}.
     */
    fun transformRatioToPresentationModel(ratio: Ratio) = with(ratio) {
        RatioPresentationModel(this.ratio)
    }

    /**
     * Transform a {@link [RatioPresentationModel]} into an {@link [Ratio]}.
     *
     * @param ratioPresentationModel Object to be transformed.
     * @return {@link [Ratio]}.
     */
    fun transformPresentationModelToRatio(ratioPresentationModel: RatioPresentationModel) =
        with(ratioPresentationModel) {
            Ratio(this.ratio)
        }

}
