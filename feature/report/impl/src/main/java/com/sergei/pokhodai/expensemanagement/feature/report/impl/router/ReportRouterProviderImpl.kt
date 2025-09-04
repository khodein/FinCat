package com.sergei.pokhodai.expensemanagement.feature.report.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ReportFragment
import com.sergei.pokhodai.expensemanagement.feature.report.impl.router.contract.ReportContract

internal class ReportRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = ReportFragment::class,
                    contract = ReportContract::class
                )
            )
        )
    }
}