package com.android.pokhodai.expensemanagement.data.models

data class User(
    val firstName: String,
    val secondName: String,
    val email: String,
) {
    fun fullName(): String = buildString {
        append(firstName)
        append(' ')
        append(secondName)
    }
}