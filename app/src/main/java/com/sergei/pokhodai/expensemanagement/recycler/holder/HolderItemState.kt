package com.sergei.pokhodai.expensemanagement.recycler.holder

import android.content.Context
import android.view.View

interface HolderItemState {
    fun getView(context: Context): View
    val viewType: Int
}