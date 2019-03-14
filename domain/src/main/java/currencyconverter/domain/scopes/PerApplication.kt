package currencyconverter.domain.scopes

import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the application
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication
