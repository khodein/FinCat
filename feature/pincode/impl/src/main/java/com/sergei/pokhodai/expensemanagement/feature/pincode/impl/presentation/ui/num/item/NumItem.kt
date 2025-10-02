package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num.item

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class NumItem {

    data class State(
        override val provideId: String
    ) : RecyclerState
}