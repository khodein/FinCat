package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.getColorInt(@ColorRes id: Int) = ContextCompat.getColor(this, id)
fun Context.getResourceDrawable(@DrawableRes id: Int) =
    ResourcesCompat.getDrawable(this.resources, id, this.theme)
fun Context.getResourceString(@StringRes id: Int): String = this.getString(id)
fun Context.getResourceStringArray(@ArrayRes id: Int): Array<String> = this.resources.getStringArray(id)

fun Context.getToolBarHeight(): Int {
    val attrs = intArrayOf(android.R.attr.actionBarSize)
    val ta: TypedArray = this.obtainStyledAttributes(attrs)
    val toolBarHeight = ta.getDimensionPixelSize(0, -1)
    ta.recycle()
    return toolBarHeight
}