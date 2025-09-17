package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class FaqItem {

    data class State(
        override val provideId: String,
        val title: String,
        val description: String,
        val padding: ViewRect.Dp = ZERO,
        var isExpanded: Boolean = false,
        val onClickExpanded: ((state: State) -> Unit)? = null
    ) : RecyclerState
}