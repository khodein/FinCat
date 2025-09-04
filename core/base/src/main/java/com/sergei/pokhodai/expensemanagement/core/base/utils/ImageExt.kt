package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageColorFilterValue
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue

fun ImageView.load(value: ImageValue?) {
    when (value) {
        is ImageValue.Res -> setImageResource(value.value)
        else -> setImageDrawable(null)
    }
}

fun ImageView.setColorFilter(value: ImageColorFilterValue?) {
    when (value) {
        is ImageColorFilterValue.Disabled -> {
            val matrixWithSaturation = ColorMatrix().apply {
                setSaturation(0f)
                postConcat(
                    ColorMatrix(
                        floatArrayOf(
                            1f, 0f, 0f, 0f, 0f,
                            0f, 1f, 0f, 0f, 0f,
                            0f, 0f, 1f, 0f, 0f,
                            0f, 0f, 0f, value.alpha, 0f
                        )
                    )
                )
            }
            colorFilter = ColorMatrixColorFilter(matrixWithSaturation)
        }

        is ImageColorFilterValue.Tint -> {
            val colorMatrix = ColorMatrix().apply {
                setSaturation(1f)
                val colorValue = value.tintColor.getColor(context)
                val (red, green, blue) = colorValue.let { color ->
                    Triple(
                        Color.red(color).toFloat(),
                        Color.green(color).toFloat(),
                        Color.blue(color).toFloat()
                    )
                }

                postConcat(
                    ColorMatrix(
                        floatArrayOf(
                            0f, 0f, 0f, 0f, red,
                            0f, 0f, 0f, 0f, green,
                            0f, 0f, 0f, 0f, blue,
                            0f, 0f, 0f, value.alpha, 0f
                        )
                    )
                )
            }

            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }

        else -> clearColorFilter()
    }
}