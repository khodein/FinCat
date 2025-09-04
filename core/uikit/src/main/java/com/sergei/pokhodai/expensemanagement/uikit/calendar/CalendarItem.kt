package com.sergei.pokhodai.expensemanagement.uikit.calendar

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class CalendarItem {

    data class State(
        override val provideId: String,
        val text: String,
        val onClickCalendar: (() -> Unit)? = null,
        val onClickLeft: (() -> Unit)? = null,
        val onClickRight: (() -> Unit)? = null,
    ): RecyclerState
}