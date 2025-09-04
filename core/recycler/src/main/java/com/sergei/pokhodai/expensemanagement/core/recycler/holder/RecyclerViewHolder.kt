package com.sergei.pokhodai.expensemanagement.core.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class RecyclerViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    @Suppress("UNCHECKED_CAST")
    fun <T : RecyclerState> bind(state: T) {
        (view as? RecyclerItemView<T>)?.bindState(state)
    }
}