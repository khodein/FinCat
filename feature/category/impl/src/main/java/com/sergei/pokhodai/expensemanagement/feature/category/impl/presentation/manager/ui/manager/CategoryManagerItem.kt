package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class CategoryManagerItem {

    data class State(
        override val provideId: String,
        val categoryKindItemState: CategoryKindItem.State,
        val name: String,
        val budgetName: String,
        val data: Any? = null,
        val onClickEdit: ((state: State) -> Unit)? = null,
        val onClickDelete: ((state: State) -> Unit)? = null
    ) : RecyclerState
}