package com.sergei.pokhodai.expensemanagement.uikit.toolbar

import androidx.annotation.StyleRes
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.uikit.R as uikitR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO

class ToolbarItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val navigateIcon: NavigateIcon? = NavigateIcon(),
        val title: Title,
        val isDelete: Boolean = false,
        val backgroundColor: ColorValue = ColorValue.Res(R.color.grey_100),
        val onClickDelete: (() -> Unit)? = null,
        val onClickNavigation: (() -> Unit)? = null
    )

    data class Title(
        val value: String,
        @StyleRes val styleRes: Int = R.style.Medium_16,
        val textColor: ColorValue = ColorValue.Res(R.color.grey_800)
    )

    data class NavigateIcon(
        val color: ColorValue = ColorValue.Res(R.color.grey_800),
        val icon: ImageValue = ImageValue.Res(uikitR.drawable.ic_arrow_back)
    )
}