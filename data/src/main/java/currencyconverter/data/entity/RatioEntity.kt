package currencyconverter.data.entity

import com.google.gson.annotations.Expose

/**
 * Class that represents a RatioEntity in the data layer.
 */
data class RatioEntity(
    @Expose
    val ratio: Map<String, Double>
)
