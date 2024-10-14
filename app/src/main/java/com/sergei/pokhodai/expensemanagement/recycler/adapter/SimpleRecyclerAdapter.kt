package com.sergei.pokhodai.expensemanagement.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.recycler.base.BaseRecyclerAdapter
import com.sergei.pokhodai.expensemanagement.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.recycler.diff.RecyclerAdapterDiffCallback

class SimpleRecyclerAdapter : ListAdapter<RecyclerState, RecyclerView.ViewHolder>(
    RecyclerAdapterDiffCallback()
) {

    private val baseAdapter = BaseRecyclerAdapter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return baseAdapter.onCreateViewHolder(
            parent = parent,
            viewType = viewType
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        baseAdapter.onBindViewHolder(
            item = getItem(position),
            holder = holder,
        )
    }

    override fun getItemViewType(position: Int) = baseAdapter.getItemViewType(item = getItem(position))
}