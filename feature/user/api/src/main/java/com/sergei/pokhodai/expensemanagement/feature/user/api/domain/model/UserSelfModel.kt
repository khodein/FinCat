package com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model

data class UserSelfModel(
    val userId: Long? = null,
    val name: String,
    val email: String,
    val avatar: UserAvatarModel? = null,
    val currency: UserCurrencyModel? = null,
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
                avatar = null,
                currency = null
            )
        }
    }
}