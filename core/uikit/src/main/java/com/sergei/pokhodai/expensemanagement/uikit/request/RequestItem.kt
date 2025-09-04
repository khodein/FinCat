package com.sergei.pokhodai.expensemanagement.uikit.request

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class RequestItem {

    sealed interface State : RecyclerState {

        override val provideId: String
        val container: Container
            get() = Container()

        data object Loading : State {
            override val provideId: String = "request_state_loading_id"
        }

        data class Error(
            val message: String,
            override val container: Container = Container(),
            val onClickReload: (() -> Unit)? = null,
        ) : State {
            override val provideId: String = "request_state_error_id"
        }

        data class Empty(
            val message: String,
            override val container: Container = Container()
        ) : State {
            override val provideId: String = "request_state_empty_id"
        }

        data object Idle : State {
            override val provideId: String = "request_state_idle_id"
        }
    }

    data class Container(
        val width: ViewDimension = ViewDimension.MatchParent,
        val height: ViewDimension = ViewDimension.MatchParent,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT),
        val paddings: ViewRect.Dp = ZERO,
    )
}