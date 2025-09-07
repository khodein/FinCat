package com.sergei.pokhodai.expensemanagement.feature.user.api.router

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel

interface UserRouter {
    fun goToUser(userId: Long? = null)
    fun goToUserLanguage()
    fun goToUserCurrency(model: UserCurrencyModel)
    fun goToUserAvatar(model: UserAvatarModel? = null)
    fun goToUserList()
}