package currencyconverter.presentation.utils.extension

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import currencyconverter.presentation.R

fun AppCompatActivity.snackbar(view: View, message: Int) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

fun AppCompatActivity.snackbar(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

fun AppCompatActivity.animateChangingActivityFade() = overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

fun AppCompatActivity.animateChangingActivitySlideIn() =
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

fun AppCompatActivity.animateChangingActivitySlideOut() =
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
