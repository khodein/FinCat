package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.router.ExchangeRateRouter
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.contact.ExchangeRateContract

internal class ExchangeRateRouterImpl(
    private val router: Router,
) : ExchangeRateRouter {

    override fun goToExchangeRate() {
        router.navigate(
            navAnimation = NavAnimation.FADE,
            contract = ExchangeRateContract()
        )
    }
}