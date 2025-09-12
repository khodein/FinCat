package com.sergei.pokhodai.expensemanagement.feature.calendar.api

import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel

interface CalendarMonthRouter {
    fun goToCalendarMonth(
        month: CalendarMonthModel,
        isHome: Boolean = false,
        isReport: Boolean = false,
    )
}