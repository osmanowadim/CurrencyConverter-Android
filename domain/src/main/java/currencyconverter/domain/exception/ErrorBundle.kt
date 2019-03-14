package currencyconverter.domain.exception

/**
 * Interface to represent a wrapper around an {@link [java.lang.Exception]} to manage errors.
 */
interface ErrorBundle {

    fun getException(): Exception

    fun getErrorMessage(): String

}
