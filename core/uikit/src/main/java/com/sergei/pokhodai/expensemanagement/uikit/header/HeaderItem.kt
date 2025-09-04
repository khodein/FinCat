package com.sergei.pokhodai.expensemanagement.uikit.header

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class HeaderItem {

    data class State(
        override val provideId: String,
        val title: String,
        val sum: String? = null,
    ): RecyclerState
}