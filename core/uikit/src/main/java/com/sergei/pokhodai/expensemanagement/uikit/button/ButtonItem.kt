package com.sergei.pokhodai.expensemanagement.uikit.button

import android.graphics.Color
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class ButtonItem {

    data class State(
        override val provideId: String,
        val width: ViewDimension = ViewDimension.WrapContent,
        val height: ViewDimension = ViewDimension.WrapContent,
        val radius: ViewDimension.Dp = ViewDimension.Dp(8),
        val isLoading: Boolean = false,
        val value: String,
        val isEnabled: Boolean = true,
        val icon: ImageValue? = null,
        val fill: Fill,
        val data: Any? = null,
        val container: Container = Container(),
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO,
        val backgroundColor: ColorValue = ColorValue.Color(Color.TRANSPARENT)
    )

    sealed class Fill(
        val textColor: ColorValue,
        val backgroundColor: ColorValue,
        val rippleColor: ColorValue,
        val iconColor: ColorValue,
        val indicatorColor: ColorValue,
    ) {
        data object Filled : Fill(
            textColor = ColorValue.Res(R.color.white),
            backgroundColor = ColorValue.Res(R.color.blue_500),
            rippleColor = ColorValue.Res(R.color.blue_700),
            iconColor = ColorValue.Res(R.color.grey_50),
            indicatorColor = ColorValue.Res(R.color.background)
        )

        data class Outline(
            val borderColor: ColorValue = ColorValue.Res(R.color.grey_400)
        ) : Fill(
            textColor = ColorValue.Res(R.color.blue_600),
            backgroundColor = ColorValue.Res(android.R.color.transparent),
            rippleColor = ColorValue.Res(R.color.blue_400),
            iconColor = ColorValue.Res(R.color.blue_500),
            indicatorColor = ColorValue.Res(R.color.blue_500)
        )

        data object Text : Fill(
            textColor = ColorValue.Res(R.color.blue_600),
            backgroundColor = ColorValue.Res(android.R.color.transparent),
            rippleColor = ColorValue.Res(R.color.blue_50),
            iconColor = ColorValue.Res(R.color.blue_500),
            indicatorColor = ColorValue.Res(R.color.blue_500)
        )
    }
}