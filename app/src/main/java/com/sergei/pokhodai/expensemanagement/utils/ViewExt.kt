package com.sergei.pokhodai.expensemanagement.utils

import android.content.res.ColorStateList
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun View.getDrawable(@DrawableRes id: Int) = ResourcesCompat.getDrawable(context.resources, id, context.theme)
fun View.getColor(@ColorRes id: Int) = ContextCompat.getColor(context, id)
fun View.getColorStateList(@ColorRes id: Int) = ContextCompat.getColorStateList(context, id)

fun View.applyPadding(
    left: Int = this.paddingLeft,
    top: Int = this.paddingTop,
    right: Int = this.paddingRight,
    bottom: Int = this.paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.applyMargin(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    val lp = this.layoutParams
    if (lp !is ViewGroup.MarginLayoutParams) return

    val isNeedChange = lp.leftMargin != left ||
            lp.topMargin != top ||
            lp.rightMargin != right ||
            lp.bottomMargin != bottom

    if (isNeedChange) {
        lp.leftMargin = left
        lp.topMargin = top
        lp.rightMargin = right
        lp.bottomMargin = bottom
        this.layoutParams = lp
    }
}

fun View.makeRounded(radius: Int) {
    this.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0,
                0,
                view.width,
                view.height,
                radius.toFloat()
            )
        }
    }

    this.clipToOutline = true
}