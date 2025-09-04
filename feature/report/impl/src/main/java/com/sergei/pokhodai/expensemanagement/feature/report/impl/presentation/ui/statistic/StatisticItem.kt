package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue

internal class StatisticItem {

    data class State(
        override val provideId: String,
        val cells: List<Cell>
    ): RecyclerState

    data class Cell(
        val color: ColorValue,
        val percent: Int,
    )
}