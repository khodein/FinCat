package com.sergei.pokhodai.expensemanagement.feature.calendar.presentation.mapper

import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel

interface CalendarMonthNameMapper {
    fun getShortName(model: CalendarMonthModel): String
}