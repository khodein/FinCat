package com.android.pokhodai.expensemanagement.data.models

import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter

data class User(
    val firstName: String,
    val secondName: String,
    val email: String,
    val birth: LocalDateFormatter,
) {
    fun fullName(): String = buildString {
        append(firstName)
        append(' ')
        append(secondName)
    }
}