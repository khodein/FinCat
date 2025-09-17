package com.sergei.pokhodai.expensemanagement.feature.faq.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.FaqFragment
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.router.contract.FaqContract

internal class FaqRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    contract = FaqContract::class,
                    clazz = FaqFragment::class
                )
            )
        )
    }
}