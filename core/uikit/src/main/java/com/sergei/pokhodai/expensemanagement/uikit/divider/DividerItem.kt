package com.sergei.pokhodai.expensemanagement.uikit.divider

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class DividerItem {

    sealed class State(
        override val provideId: String,
    ) : RecyclerState {
        data class Empty(
            override val provideId: String,
            val height: ViewDimension.Dp,
        ) : State(provideId = provideId)

        data class Horizontal(
            override val provideId: String,
            val dividerColor: ColorValue = ColorValue.Color(Color.TRANSPARENT),
            val paddings: ViewRect = ZERO,
            val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT),
            val dividerThickness: ViewDimension.Dp = ViewDimension.Dp(0),
        ) : State(provideId = provideId)
    }
}