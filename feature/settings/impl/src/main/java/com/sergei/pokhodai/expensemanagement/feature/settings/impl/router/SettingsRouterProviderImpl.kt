package com.sergei.pokhodai.expensemanagement.feature.settings.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.SettingsFragment
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.router.contract.SettingsContract

internal class SettingsRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = SettingsFragment::class,
                    contract = SettingsContract::class
                )
            )
        )
    }
}