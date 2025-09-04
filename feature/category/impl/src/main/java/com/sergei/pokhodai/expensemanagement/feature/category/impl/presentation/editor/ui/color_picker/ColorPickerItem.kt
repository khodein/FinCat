package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.color_picker

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class ColorPickerItem {

    data class State(
        override val provideId: String,
        val color: ColorValue,
        val name: String,
        val container: Container = Container(),
        val onClick: (() -> Unit)? = null
    ): RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT)
    )
}