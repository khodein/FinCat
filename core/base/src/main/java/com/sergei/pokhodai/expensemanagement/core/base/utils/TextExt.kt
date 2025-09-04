package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.sergei.pokhodai.expensemanagement.core.base.style.TextStyleValue

fun TextView.setAppearance(@StyleRes textAppearance: Int) {
    TextViewCompat.setTextAppearance(this, textAppearance)
}

fun TextView.setTextStyleValue(value: TextStyleValue) {
    when(value) {
        is TextStyleValue.Res -> {
            setAppearance(value.value)
        }
    }
}


fun EditText.setUniqueText(newText: String?) {
    if (text?.toString() != newText) {
        setText(newText)
    }
}