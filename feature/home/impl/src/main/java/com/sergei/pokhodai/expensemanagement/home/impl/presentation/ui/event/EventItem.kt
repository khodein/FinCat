package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class EventItem {

    data class State(
        override val provideId: String,
        val categoryKindItemState: CategoryKindItem.State,
        val name: CharSequence,
        val amount: CharSequence,
        val data: Any? = null,
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState
}