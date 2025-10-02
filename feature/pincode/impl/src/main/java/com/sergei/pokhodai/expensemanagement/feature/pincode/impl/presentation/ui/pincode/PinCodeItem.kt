package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.pincode

class PinCodeItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val status: Status,
        val onClick: ((String) -> Unit)? = null
    )

    enum class Status {
        LOADING,
        ERROR,
        SUCCESS,
        IDLE
    }
}