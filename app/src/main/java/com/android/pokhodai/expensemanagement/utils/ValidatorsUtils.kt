package com.android.pokhodai.expensemanagement.utils

import android.util.Patterns
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatorsUtils @Inject constructor() {

    fun validateEmail(hex: String): Boolean {
        val matcher = Patterns.EMAIL_ADDRESS.matcher(hex)
        return matcher.matches()
    }
}