package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ExchangeRateFragment
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.contact.ExchangeRateContract

internal class ExchangeRateRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = ExchangeRateFragment::class,
                    contract = ExchangeRateContract::class
                )
            )
        )
    }
}