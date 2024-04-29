package com.sergei.pokhodai.expensemanagement.uikit.button

import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.utils.ViewDimension

class ButtonItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val id: String,
        val text: String,
        val margins: DpRect = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
        val containerPadding: DpRect = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
        @ColorRes val containerBackgroundRes: Int = R.color.transparent,
        val cornerRadius: ViewDimension.Dp = ViewDimension.Dp(5),
        val isEnabled: Boolean = true,
        val style: Style = Style.Filled(),
        val size: Size = Size.DEFAULT,
        val onClick: (() -> Unit)? = null
    )

    sealed class Style(
        open val isLeading: Boolean = false,
        open val leadingDrawableRes: Int,
        @StyleRes open val textStyleRes: Int,
        @ColorRes open val backgroundColorRes: Int
    ) {
        data class Filled(
            override val isLeading: Boolean = false,
            override val leadingDrawableRes: Int = R.drawable.ic_button_filled,
            @StyleRes override val textStyleRes: Int = R.style.Regular_14_White,
            @ColorRes override val backgroundColorRes: Int = R.color.blue_500
        ) : Style(
            isLeading = isLeading,
            textStyleRes = textStyleRes,
            leadingDrawableRes = leadingDrawableRes,
            backgroundColorRes = backgroundColorRes
        )

        data class Outlined(
            override val isLeading: Boolean = false,
            override val leadingDrawableRes: Int = R.drawable.ic_button_outline,
            val strokeWidth: ViewDimension.Dp = ViewDimension.Dp(1),
            @ColorRes val strokeColorRes: Int = R.color.grey_400,
            @StyleRes override val textStyleRes: Int = R.style.Regular_14_Blue600,
            @ColorRes override val backgroundColorRes: Int = R.color.white
        ) : Style(
            isLeading = isLeading,
            textStyleRes = textStyleRes,
            leadingDrawableRes = leadingDrawableRes,
            backgroundColorRes = backgroundColorRes
        )

        data class Text(
            override val isLeading: Boolean = false,
            override val leadingDrawableRes: Int = R.drawable.ic_button_outline,
            @StyleRes override val textStyleRes: Int = R.style.Regular_14_Blue600,
            @ColorRes override val backgroundColorRes: Int = R.color.transparent
        ) : Style(
            isLeading = isLeading,
            textStyleRes = textStyleRes,
            leadingDrawableRes = leadingDrawableRes,
            backgroundColorRes = backgroundColorRes
        )
    }

    data class Size(
        val width: ViewDimension,
        val height: ViewDimension
    ) {
        companion object {
            val DEFAULT = Size(
                width = ViewDimension.WrapContent,
                height = ViewDimension.WrapContent
            )
        }
    }
}