package com.sergei.pokhodai.expensemanagement.feature.user.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.UserCurrencyDialog
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.UserLanguageDialog
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.UserFragment
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserCurrencyContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserLanguageContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserRouterContract

internal class UserRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = UserFragment::class,
                    contract = UserRouterContract::class
                )
            ),
            RouteDestination(
                type = RouteDestination.Type.DialogType(
                    clazz = UserLanguageDialog::class,
                    contract = UserLanguageContract::class
                )
            ),
            RouteDestination(
                type = RouteDestination.Type.DialogType(
                    clazz = UserCurrencyDialog::class,
                    contract = UserCurrencyContract::class
                )
            )
        )
    }
}