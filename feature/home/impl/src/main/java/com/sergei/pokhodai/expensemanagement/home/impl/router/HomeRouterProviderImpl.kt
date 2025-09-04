package com.sergei.pokhodai.expensemanagement.home.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.HomeFragment
import com.sergei.pokhodai.expensemanagement.home.impl.router.contract.HomeContract

internal class HomeRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = HomeFragment::class,
                    contract = HomeContract::class
                )
            )
        )
    }
}