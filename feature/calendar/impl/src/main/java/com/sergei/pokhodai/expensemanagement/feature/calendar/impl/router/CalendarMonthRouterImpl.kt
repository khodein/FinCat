package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.calendar.api.CalendarMonthRouter
import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.contract.CalendarMonthContract

internal class CalendarMonthRouterImpl(
    private val router: Router,
) : CalendarMonthRouter {
    override fun goToCalendarMonth(
        month: CalendarMonthModel,
        isHome: Boolean,
        isReport: Boolean,
    ) {
        router.navigate(
            contract = CalendarMonthContract(
                month = month.name,
                isReport = isReport,
                isHome = isHome,
            )
        )
    }
}