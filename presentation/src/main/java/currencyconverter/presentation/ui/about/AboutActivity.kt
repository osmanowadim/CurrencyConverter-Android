package currencyconverter.presentation.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import currencyconverter.presentation.R
import currencyconverter.presentation.utils.extension.animateChangingActivityFade

fun Context.aboutActivityIntent() = Intent(this, AboutActivity::class.java)

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onBackPressed() {
        animateChangingActivityFade()
        finish()
    }

}
