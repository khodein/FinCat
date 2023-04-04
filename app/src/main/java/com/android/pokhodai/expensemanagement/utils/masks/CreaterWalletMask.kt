package com.android.pokhodai.expensemanagement.utils.masks

import android.text.Editable
import android.text.TextWatcher
import javax.inject.Inject

class CreaterWalletMask @Inject constructor(
): TextWatcher {
    private var mWasEdited = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        if (mWasEdited) {
            mWasEdited = false
            return
        }

        var enteredValue = s.toString()
        var count = 0
        enteredValue.forEachIndexed { index, c ->
            if (c.toString() == "-") {
                count++
                if (count == 2) {
                    enteredValue  = StringBuffer(enteredValue).apply {
                        delete(index, index+1)
                    }.toString()
                }
            }
        }
        val newValue = buildString {
            if (enteredValue.isEmpty()) {
                append("-")
            }
            append(enteredValue)
        }

        mWasEdited = true
        s.replace(0, s.length, newValue)
    }
}