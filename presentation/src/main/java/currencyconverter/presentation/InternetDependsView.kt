package currencyconverter.presentation


interface InternetDependsView {

    fun isInternetAvailable(): Boolean

    fun showNoInternetConnection()

}
