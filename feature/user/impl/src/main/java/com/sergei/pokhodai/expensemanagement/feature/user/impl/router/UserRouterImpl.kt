package com.sergei.pokhodai.expensemanagement.feature.user.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserCurrencyContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserLanguageContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserRouterContract

internal class UserRouterImpl(
    private val router: Router,
) : UserRouter {

    override fun goToUser(userId: Int?) {
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
}