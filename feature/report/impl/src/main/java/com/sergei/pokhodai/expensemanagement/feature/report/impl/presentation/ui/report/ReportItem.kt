package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.report

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class ReportItem {

    data class State(
        override val provideId: String,
        val categoryKindItemState: CategoryKindItem.State,
        val title: String,
        val subTitle: String,
        val amount: CharSequence,
        val percent: String,
    ): RecyclerState
}