package com.sergei.pokhodai.expensemanagement.uikit.dropdown

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class DropDownItem {

    data class State(
        override val provideId: String,
        val line: Line = Line(),
        val container: Container = Container(),
        val hint: String? = null,
        var value: String? = null,
        val items: List<Item>,
        val onClickItem: ((item: Item) -> Unit)? = null,
    ): RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT)
    )

    data class Line(
        val maxLine: Int = 1,
        val minLine: Int = 1,
        val lines: Int = 1,
    )

    data class Item(
        val value: String,
        val data: Any? = null,
    )
}