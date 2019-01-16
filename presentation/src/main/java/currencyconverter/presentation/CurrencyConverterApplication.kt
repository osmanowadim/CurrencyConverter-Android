package currencyconverter.presentation

import android.app.Activity
import android.app.Application
import currencyconverter.presentation.di.components.ApplicationComponent
import currencyconverter.presentation.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class CurrencyConverterApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        injectMembers()
    }

    private fun injectMembers() {
        appComponent.inject(this)
    }

}
