package com.sergei.pokhodai.expensemanagement.feature.user.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserAvatarContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserCurrencyContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserLanguageContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserListContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserRouterContract

internal class UserRouterImpl(
    private val router: Router,
) : UserRouter {

    override fun goToUser(userId: Long?) {
        router.navigate(
            navAnimation = NavAnimation.FADE,
            contract = UserRouterContract(
                userId = userId
            )
        )
    }

    override fun goToUserLanguage() {
        router.navigate(contract = UserLanguageContract())
    }

    override fun goToUserCurrency(model: UserCurrencyModel) {
        router.navigate(contract = UserCurrencyContract(model.name))
    }

    override fun goToUserAvatar(model: UserAvatarModel?) {
        router.navigate(contract = UserAvatarContract(avatar = model?.name))
    }

    override fun goToUserList() {
        router.navigate(
            navAnimation = NavAnimation.FADE,
            contract = UserListContract()
        )
    }
}