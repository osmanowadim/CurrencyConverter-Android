package currencyconverter.data.exception

import currencyconverter.domain.exception.ErrorBundle

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
class RepositoryErrorBundle(private val exception: Exception) : ErrorBundle {

    override fun getException() = exception

    override fun getErrorMessage() = exception.message ?: DEFAULT_ERROR_MSG

    companion object {

        private const val DEFAULT_ERROR_MSG = "Unknown error"

    }

}
