package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.createBitmap

fun Drawable.getDrawableToBitmap(
    width: Float? = null,
    height: Float? = null
): Bitmap {
    if (this is BitmapDrawable) {
        return this.bitmap
    }

    val bitmap = createBitmap(
        width?.toInt() ?: this.intrinsicWidth,
        height?.toInt() ?: this.intrinsicHeight
    )

    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, canvas.width, canvas.height)
    this.draw(canvas)

    return bitmap
}