package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.CalendarMonthDialog
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.contract.CalendarMonthContract

internal class CalendarMonthRouterProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.DialogType(
                    clazz = CalendarMonthDialog::class,
                    contract = CalendarMonthContract::class
                ),
            )
        )
    }
}