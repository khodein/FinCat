package com.sergei.pokhodai.expensemanagement.home.impl.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp

internal class HomeDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = 16.dp
        outRect.left = 16.dp
        outRect.right = 16.dp
    }
}