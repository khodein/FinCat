package com.sergei.pokhodai.expensemanagement.core.support.api.model.calendar

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter

class CalendarRouterModel(
    val start: LocalDateFormatter,
    val end: LocalDateFormatter,
    val value: LocalDateFormatter,
    val onClick: (newValue: LocalDateFormatter) -> Unit
)