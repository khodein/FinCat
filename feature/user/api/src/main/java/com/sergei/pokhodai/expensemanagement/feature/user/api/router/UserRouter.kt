package com.sergei.pokhodai.expensemanagement.feature.user.api.router

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel

interface UserRouter {
    fun goToUser(userId: Int? = null)
    fun goToUserLanguage()
    fun goToUserCurrency(model: UserCurrencyModel)
}