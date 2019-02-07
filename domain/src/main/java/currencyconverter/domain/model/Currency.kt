package currencyconverter.domain.model

import currencyconverter.domain.interactor.Params

/**
 * Class that represents a Currency in the domain layer.
 */
class Currency(
    val currencyName: String,
    val currencySymbol: String?,
    val id: String
) : Params()
