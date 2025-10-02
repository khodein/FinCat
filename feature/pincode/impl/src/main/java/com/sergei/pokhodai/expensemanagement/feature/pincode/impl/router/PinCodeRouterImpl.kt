package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.pincode.api.PinCodeRouter
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.contract.PinCodeContract

internal class PinCodeRouterImpl(
    private val router: Router,
) : PinCodeRouter {
    override fun goToPinCode() {
        router.navigate(
            navAnimation = NavAnimation.FADE,
            contract = PinCodeContract()
        )
    }
}