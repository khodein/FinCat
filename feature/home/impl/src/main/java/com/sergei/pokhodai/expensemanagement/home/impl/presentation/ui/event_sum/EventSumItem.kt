package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_sum

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class EventSumItem {

    data class State(
        override val provideId: String,
        val expenseText: CharSequence,
        val balanceText: CharSequence,
        val incomeText: CharSequence,
    ): RecyclerState
}