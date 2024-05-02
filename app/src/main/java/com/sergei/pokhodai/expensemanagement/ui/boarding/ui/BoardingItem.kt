package com.sergei.pokhodai.expensemanagement.ui.boarding.ui

import androidx.annotation.DrawableRes

class BoardingItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val id: String,
        val title: String,
        val caption: String,
        @DrawableRes val imageRes: Int,
    )
}