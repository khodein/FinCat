package com.sergei.pokhodai.expensemanagement.core.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.recycler.diff.RecyclerStateDiffCallback
import com.sergei.pokhodai.expensemanagement.core.recycler.factory.RecyclerTempFactory
import com.sergei.pokhodai.expensemanagement.core.recycler.holder.RecyclerViewHolder
import com.sergei.pokhodai.expensemanagement.core.recycler.manager.RecyclerViewTypeManager

class RecyclerAdapter() : ListAdapter<RecyclerState, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(RecyclerStateDiffCallback()).build()
) {
    private val recyclerViewTypeManager: RecyclerViewTypeManager = RecyclerViewTypeManager()
    private val asyncListDiffer = AsyncListDiffer(this, RecyclerStateDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val stateClass = recyclerViewTypeManager.getClassByType(viewType)
        val view = RecyclerTempFactory.getTemp(stateClass, parent.context)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind(getItem(position))
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    public override fun getItem(position: Int): RecyclerState = asyncListDiffer.currentList[position]

    override fun submitList(list: MutableList<RecyclerState>?, commitCallback: Runnable?) {
        asyncListDiffer.submitList(list, commitCallback)
    }

    override fun getItemViewType(position: Int): Int {
        return recyclerViewTypeManager.getType(getItem(position))
    }

    override fun submitList(list: List<RecyclerState>?) {
        asyncListDiffer.submitList(list)
    }
}