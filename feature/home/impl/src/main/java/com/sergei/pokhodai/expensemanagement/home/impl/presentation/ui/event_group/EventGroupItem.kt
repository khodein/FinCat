package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_group

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItem

internal class EventGroupItem {

    data class State(
        override val provideId: String,
        val eventHeaderState: HeaderItem.State,
        val eventStateList: List<EventItem.State>,
    ) : RecyclerState
}