package com.sergei.pokhodai.expensemanagement.utils

import android.widget.EditText

fun EditText.setUniqueText(newText: String?) {
    if (text?.toString() != newText) {
        setText(newText)
    }
}