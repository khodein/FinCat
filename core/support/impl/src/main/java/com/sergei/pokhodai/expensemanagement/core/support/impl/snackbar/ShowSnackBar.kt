package com.sergei.pokhodai.expensemanagement.core.support.impl.snackbar

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.updateMargins
import com.google.android.material.snackbar.Snackbar
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.setAppearance

internal fun onShowSnackBar(
    text: String,
    anchorView: View,
    buttonText: String? = null,
    onButtonClicked: (() -> Unit)? = null,
    lengthLong: Int = Snackbar.LENGTH_LONG,
) {
    val imm =
        anchorView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    if (imm.isActive) imm.hideSoftInputFromWindow(anchorView.windowToken, 0)

    val snackBar = Snackbar.make(
        anchorView,
        text,
        lengthLong
    )

    val snackBarView: View = snackBar.getView()
    val params = snackBarView.layoutParams as? FrameLayout.LayoutParams
    params?.gravity = Gravity.TOP
    params?.updateMargins(top = 30.dp)
    snackBarView.layoutParams = params

    val textView =
        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setAppearance(R.style.Medium_16)
            setTextColor(anchorView.getColor(R.color.grey_50))
        }
    textView.maxLines = 10

    snackBar.setBackgroundTint(anchorView.getColor(R.color.grey_800))
    snackBar.setTextColor(anchorView.getColor(R.color.grey_50))

    snackBar.setActionTextColor(anchorView.getColor(R.color.grey_50))

    if (buttonText != null) {
        snackBar.setAction(buttonText) {
            onButtonClicked?.invoke() ?: snackBar.dismiss()
        }
        snackBar.duration = Snackbar.LENGTH_INDEFINITE
    }

    val callback = object : Snackbar.Callback() {
        override fun onDismissed(snackbar: Snackbar, event: Int) {
            if (event == DISMISS_EVENT_TIMEOUT) {
                ViewCompat.setOnApplyWindowInsetsListener(snackBar.view, null)
                snackBar.removeCallback(this)
            }
        }
    }
    snackBar.addCallback(callback)
    snackBar.show()
}