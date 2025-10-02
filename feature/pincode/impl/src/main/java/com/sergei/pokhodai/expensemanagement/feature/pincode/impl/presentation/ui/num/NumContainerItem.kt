package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num

class NumContainerItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val onClick: ((String) -> Unit)? = null
    )
}