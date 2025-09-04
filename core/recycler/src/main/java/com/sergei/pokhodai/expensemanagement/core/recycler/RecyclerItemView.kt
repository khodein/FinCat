package com.sergei.pokhodai.expensemanagement.core.recycler

interface RecyclerItemView<T : RecyclerState> {
    fun bindState(state: T)
}