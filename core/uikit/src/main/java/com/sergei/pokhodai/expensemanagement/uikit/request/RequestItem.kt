package com.sergei.pokhodai.expensemanagement.uikit.request

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class RequestItem {

    sealed interface State : RecyclerState {

        val container: Container
            get() = Container()
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
            override val container: Container = Container(),
            val buttonText: String? = null,
            val onClickEmpty: (() -> Unit)? = null
        ) : State {
            override val provideId: String = "request_state_empty_id"
        }

        data object Idle : State {
            override val provideId: String = "request_state_idle_id"
        }

        data class Container(
            val width: ViewDimension = ViewDimension.MatchParent,
            val height: ViewDimension = ViewDimension.MatchParent,
            val paddings: ViewRect.Dp = ZERO
        )
    }
}