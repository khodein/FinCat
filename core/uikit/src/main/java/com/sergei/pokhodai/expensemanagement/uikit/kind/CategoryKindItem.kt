package com.sergei.pokhodai.expensemanagement.uikit.kind

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class CategoryKindItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val icon: ImageValue?,
        val color: ColorValue,
        val data: Any? = null,
        val name: String? = null,
        val onClick: ((state: State) -> Unit)? = null
    ): RecyclerState

    data class Container(
        val padding: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT),
        val width: ViewDimension = ViewDimension.WrapContent,
        val height: ViewDimension = ViewDimension.WrapContent,
    )
}