package com.sergei.pokhodai.expensemanagement.core.base.color

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.graphics.toColorInt

object GraphicUtils {
    @Throws(Exception::class)
    @ColorInt
    private fun parse(colorString: String): Int {
        val string = if (colorString.length == 4) {
            "#" + colorString.substring(1, 2).repeat(2) +
                    colorString.substring(2, 3).repeat(2) +
                    colorString.substring(3, 4).repeat(2)
        } else {
            colorString
        }

        return string.toColorInt()
    }

    @ColorInt
    fun parseColor(colorString: String?, @ColorInt defaultColor: Int): Int {
        if (colorString == null) return defaultColor

        return try {
            parse(colorString)
        } catch (e: Exception) {
            defaultColor
        }
    }

    @ColorInt
    fun parseColorOrNull(colorString: String?): Int? {
        if (colorString.isNullOrEmpty()) return null

        return try {
            parse(colorString)
        } catch (e: Exception) {
            null
        }
    }
}