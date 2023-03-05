package com.android.pokhodai.expensemanagement.utils

import android.content.res.Resources
import android.view.View
import android.view.Window
import androidx.core.view.updateLayoutParams

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.px: Float
    get() = (this / Resources.getSystem().displayMetrics.density)