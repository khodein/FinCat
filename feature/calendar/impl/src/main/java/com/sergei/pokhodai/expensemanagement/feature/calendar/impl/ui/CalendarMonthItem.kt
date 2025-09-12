package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.ui

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem

internal class CalendarMonthItem {

    data class State(
        override val provideId: String,
        val tagItemState: TagItem.State,
    ) : RecyclerState
}