package currencyconverter.presentation.utils.extension

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.visible(isVisible: Boolean) = if (isVisible) this.show() else this.hide()
