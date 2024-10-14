package com.sergei.pokhodai.expensemanagement.recycler

interface RecyclerItemView<T : RecyclerState> {
    fun bindState(state: T)
}