package com.sergei.pokhodai.expensemanagement.core.base.color

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

sealed interface ColorValue {
    data class Res(@ColorRes val value: Int) : ColorValue
    data class Color(@ColorInt val value: Int) : ColorValue

    @ColorInt
    fun getColor(context: Context): Int {
        return when (this) {
            is Res -> context.getColor(this.value)
            is Color -> this.value
        }
    }

    companion object {
        fun parseColor(colorString: String?, @ColorInt colorInt: Int = android.graphics.Color.TRANSPARENT): ColorValue {
            return GraphicUtils.parseColorOrNull(colorString)?.let {
                Color(it)
            } ?: Color(colorInt)
        }
    }
}