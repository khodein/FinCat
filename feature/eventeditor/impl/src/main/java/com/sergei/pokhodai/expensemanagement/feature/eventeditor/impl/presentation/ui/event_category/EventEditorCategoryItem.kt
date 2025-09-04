package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.ui.event_category

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.select.SelectItem

internal class EventEditorCategoryItem {

    data class State(
        override val provideId: String,
        val selectItemState: SelectItem.State,
        val kindItemState: CategoryKindItem.State? = null,
        val paddings: ViewRect.Dp = ZERO,
        val onClick: (() -> Unit)? = null,
    ) : RecyclerState
}