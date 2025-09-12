package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class CalendarMonthContract(
    val month: String,
    val isHome: Boolean,
    val isReport: Boolean,
) : RouterContract