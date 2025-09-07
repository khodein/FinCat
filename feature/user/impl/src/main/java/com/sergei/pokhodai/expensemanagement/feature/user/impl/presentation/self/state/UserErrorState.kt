package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.state

internal data class UserErrorState(
    val isEmailError: Boolean,
    val isNameError: Boolean,
)