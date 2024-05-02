package com.sergei.pokhodai.expensemanagement.uikit.text

import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.utils.ViewDimension

class TextItem {

    interface View {
        fun bindState(state: State)
    }

    data class State(
        val id: String,
        val text: CharSequence,
        val size: Size,
        @StyleRes val textAppearance: Int? = null,
        @ColorInt val textColorInt: Int? = null,
        @ColorRes val backgroundRes: Int = R.color.transparent,
        @GravityInt val gravity: Int = Gravity.START,
        val paddings: DpRect = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
        val margins: DpRect = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
        val onClick: (() -> Unit)? = null,
    )

    data class Size(
        val width: ViewDimension = ViewDimension.WrapContent,
        val height: ViewDimension = ViewDimension.WrapContent
    )
}