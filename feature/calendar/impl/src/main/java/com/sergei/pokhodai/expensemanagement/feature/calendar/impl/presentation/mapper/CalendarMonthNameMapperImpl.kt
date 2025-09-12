package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.R
import com.sergei.pokhodai.expensemanagement.feature.calendar.presentation.mapper.CalendarMonthNameMapper

internal class CalendarMonthNameMapperImpl(
    private val resManager: ResManager,
) : CalendarMonthNameMapper {

    override fun getShortName(model: CalendarMonthModel): String {
        val resId = when(model) {
            CalendarMonthModel.MAY -> R.string.calendar_month_may_short
            CalendarMonthModel.JULY -> R.string.calendar_month_july_short
            CalendarMonthModel.JUNE -> R.string.calendar_month_june_short
            CalendarMonthModel.APRIL -> R.string.calendar_month_april_short
            CalendarMonthModel.MARCH -> R.string.calendar_month_march_short
            CalendarMonthModel.JANUARY -> R.string.calendar_month_january_short
            CalendarMonthModel.OCTOBER -> R.string.calendar_month_october_short
            CalendarMonthModel.AUGUST -> R.string.calendar_month_august_short
            CalendarMonthModel.DECEMBER -> R.string.calendar_month_december_short
            CalendarMonthModel.FEBRUARY -> R.string.calendar_month_february_short
            CalendarMonthModel.NOVEMBER -> R.string.calendar_month_november_short
            CalendarMonthModel.SEPTEMBER -> R.string.calendar_month_september_short
        }

        return resManager.getString(resId)
    }
}