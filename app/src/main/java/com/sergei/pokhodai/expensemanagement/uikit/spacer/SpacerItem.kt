package com.sergei.pokhodai.expensemanagement.uikit.spacer

import com.sergei.pokhodai.expensemanagement.utils.ViewDimension

class SpacerItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val id: String,
        val padding: ViewDimension.Dp = ViewDimension.Dp(0)
    )
}