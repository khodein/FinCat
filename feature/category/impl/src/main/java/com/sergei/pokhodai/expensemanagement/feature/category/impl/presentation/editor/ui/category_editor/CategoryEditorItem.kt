package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.category_editor

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class CategoryEditorItem {

    data class State(
        override val provideId: String,
        val textFieldItemState: TextFieldItem.State,
        val kindItemState: CategoryKindItem.State? = null,
        val paddings: ViewRect.Dp = ZERO,
        val onClickLeading: (() -> Unit)? = null,
    ) : RecyclerState
}