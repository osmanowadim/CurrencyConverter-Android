package currencyconverter.presentation.mapper

import currencyconverter.domain.model.Ratio
import currencyconverter.presentation.model.RatioPresentationModel
import javax.inject.Inject

class RatioPresentationModelMapper @Inject constructor() {

    fun transformRatioToPresentationModel(ratio: Ratio) = with(ratio) {
        RatioPresentationModel(this.ratio)
    }

    fun transformPresentationModelToRatio(ratioPresentationModel: RatioPresentationModel) =
        with(ratioPresentationModel) {
            Ratio(this.ratio)
        }

}
