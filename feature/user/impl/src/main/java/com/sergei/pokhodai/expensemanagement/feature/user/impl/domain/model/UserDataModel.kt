package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.model

internal data class UserDataModel(
    val userId: Int,
    val name: String,
    val email: String,
    val currency: String,
)