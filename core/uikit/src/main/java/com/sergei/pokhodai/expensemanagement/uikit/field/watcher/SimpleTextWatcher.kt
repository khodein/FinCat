package com.sergei.pokhodai.expensemanagement.uikit.field.watcher

import android.text.Editable
import android.text.TextWatcher
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem

internal class SimpleTextWatcher(
    private val state: TextFieldItem.State
) : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) = Unit

    override fun afterTextChanged(s: Editable?) {
        val value = s.toString()
        state.value = value
        state.onAfterTextChanger?.invoke(value)
    }
}