package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.PinCodeFragment
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.contract.PinCodeContract

internal class PinCodeRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = PinCodeFragment::class,
                    contract = PinCodeContract::class
                )
            )
        )
    }
}