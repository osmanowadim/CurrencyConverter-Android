package currencyconverter.presentation

/**
 * Interface class to act as a base for any class that is to take the role of the View
 * in the Model-View-Presenter pattern.
 */
interface BaseView<in T : BasePresenter> {

    fun setPresenter(presenter: T)

}
