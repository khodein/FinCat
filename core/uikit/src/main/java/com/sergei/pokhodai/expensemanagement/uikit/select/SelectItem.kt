package com.sergei.pokhodai.expensemanagement.uikit.select

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class SelectItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val value: String,
        val hind: String,
        val onClick: (() -> Unit)? = null
    ): RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT)
    )
}