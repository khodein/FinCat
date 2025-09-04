package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic.StatisticItemView
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItemView

internal class ReportDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (view) {
            is HeaderItemView -> outRect.left = 8.dp
            is StatisticItemView -> outRect.bottom = 8.dp
        }
    }
}