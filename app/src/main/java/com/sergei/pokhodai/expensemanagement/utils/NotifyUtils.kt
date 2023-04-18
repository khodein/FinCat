package com.sergei.pokhodai.expensemanagement.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import com.sergei.pokhodai.expensemanagement.R

fun showSnackBar(
    text: String,
    anchorView: View,
    buttonText: String? = null,
    onButtonClicked: (() -> Unit)? = null,
    lengthLong: Int = Snackbar.LENGTH_SHORT,
) {
    val imm =
        anchorView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    if (imm.isActive) imm.hideSoftInputFromWindow(anchorView.windowToken, 0)

    val snackBar = Snackbar.make(
        anchorView, text,
        lengthLong
    )

    val textView =
        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.maxLines = 10

    snackBar.setBackgroundTint(ContextCompat.getColor(anchorView.context, R.color.grey_50))

    snackBar.setTextColor(
        ContextCompat.getColor(
            anchorView.context,
            R.color.black
        )
    )

    snackBar.setActionTextColor(
        ContextCompat.getColor(
            anchorView.context,
            R.color.black
        )
    )

    ViewCompat.setOnApplyWindowInsetsListener(snackBar.view) { v, insets ->
        v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingTop)
        insets
    }

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