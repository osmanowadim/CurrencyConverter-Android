package currencyconverter.domain.exception

/**
 *  Wrapper around Exceptions used to manage default errors.
 */
class DefaultErrorBundle(private val exception: Exception) : ErrorBundle {

    override fun getException() = exception

    override fun getErrorMessage() = exception.message ?: DEFAULT_ERROR_MSG

    companion object {

        private const val DEFAULT_ERROR_MSG = "Unknown error"

    }

}
