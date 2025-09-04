package com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model

data class UserSelfModel(
    val userId: Int? = null,
    val name: String,
    val email: String,
    val currency: UserCurrencyModel,
) {
    fun isEmpty(): Boolean {
        return this == getDefault()
    }

    companion object {
        fun getDefault(): UserSelfModel {
            return UserSelfModel(
                userId = null,
                name = "",
                email = "",
                currency = UserCurrencyModel.USD
            )
        }
    }
}