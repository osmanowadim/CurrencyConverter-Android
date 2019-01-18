package currencyconverter.data.entity

import com.google.gson.annotations.Expose


data class RatioEntity(
    @Expose
    val ratio: Map<String, Double>
)
