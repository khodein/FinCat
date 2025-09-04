package com.sergei.pokhodai.expensemanagement.core.recycler

interface RecyclerState {
    val provideId: String
    fun areContentsTheSame(other: RecyclerState) = this == other
}