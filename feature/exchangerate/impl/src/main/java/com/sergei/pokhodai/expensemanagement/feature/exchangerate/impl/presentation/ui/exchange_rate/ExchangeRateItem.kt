package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ui.exchange_rate

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class ExchangeRateItem {

    data class State(
        override val provideId: String,
        val isHeader: Boolean = false,
        val name: String,
        val nominal: String,
        val rate: String,
    ) : RecyclerState
}