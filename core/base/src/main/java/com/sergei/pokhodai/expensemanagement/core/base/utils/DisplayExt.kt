package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.content.res.Resources
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Resources.getScreenHeight(): Int {
    return displayMetrics.heightPixels
}

fun Resources.getScreenWidth(): Int {
    return displayMetrics.widthPixels
}

fun View.setAllInserts() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars()
                    or WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(
            left = bars.left,
            top = bars.top,
            right = bars.right,
            bottom = bars.bottom,
        )
        WindowInsetsCompat.CONSUMED
    }
}

fun Window.setLightNavigationAndStatusBars(
    isAppearanceLightNavigationBars: Boolean = false,
    isAppearanceLightStatusBars: Boolean = false
) {
    WindowCompat.getInsetsController(this, this.decorView).apply {
        this.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
        this.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }
}