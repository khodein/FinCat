package com.sergei.pokhodai.expensemanagement.recycler

import android.content.Context
import android.view.View
import com.sergei.pokhodai.expensemanagement.recycler.holder.HolderItemState

interface RecyclerState : HolderItemState {
    val provideId: String
    override val viewType: Int
    override fun getView(context: Context): View
    fun areContentsTheSame(other: RecyclerState) = this == other
}