package com.android.pokhodai.expensemanagement.utils.masks

import android.text.Editable
import android.text.TextWatcher
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
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

        val enteredValue = s.toString()
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