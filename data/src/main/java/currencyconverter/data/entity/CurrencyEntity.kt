package currencyconverter.data.entity

/**
 * Class that represents a CurrencyEntity in the data layer.
 */
data class CurrencyEntity(
    val currencyName: String,
    val currencySymbol: String?,
    val id: String
)
