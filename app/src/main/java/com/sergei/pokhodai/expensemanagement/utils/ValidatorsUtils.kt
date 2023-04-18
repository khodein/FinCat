package com.sergei.pokhodai.expensemanagement.utils

import android.util.Patterns
import javax.inject.Inject

class ValidatorsUtils @Inject constructor() {

    fun validateEmail(hex: String): Boolean {
        val matcher = Patterns.EMAIL_ADDRESS.matcher(hex)
        return matcher.matches()
    }
}