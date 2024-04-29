package com.sergei.pokhodai.expensemanagement.utils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.px: Float
    get() = (this / Resources.getSystem().displayMetrics.density)

val Dp.toPx: Int
    get() = (this.value * Resources.getSystem().displayMetrics.density).toInt()