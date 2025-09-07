package com.sergei.pokhodai.expensemanagement.uikit.request

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class RequestItem {

    sealed interface State : RecyclerState {
        override val provideId: String

        data object Loading : State {
            override val provideId: String = "request_state_loading_id"
        }

        data class Error(
            val message: String,
            val onClickReload: (() -> Unit)? = null,
        ) : State {
            override val provideId: String = "request_state_error_id"
        }

        data class Empty(
            val message: String,
            val buttonText: String? = null,
            val onClickEmpty: (() -> Unit)? = null
        ) : State {
            override val provideId: String = "request_state_empty_id"
        }

        data object Idle : State {
            override val provideId: String = "request_state_idle_id"
        }
    }
}