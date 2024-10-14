package com.sergei.pokhodai.expensemanagement.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.recycler.RecyclerState

internal class RecyclerViewHolder(
    private val view: View,
    val viewType: Int,
) : RecyclerView.ViewHolder(view) {

    fun bind(
        state: RecyclerState,
    ) {
        (view as? RecyclerItemView<RecyclerState>)?.bindState(state)
    }
}