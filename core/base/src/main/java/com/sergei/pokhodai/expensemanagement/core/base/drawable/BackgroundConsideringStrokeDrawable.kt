package com.sergei.pokhodai.expensemanagement.core.base.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.getRadiiForRoundMode

class BackgroundConsideringStrokeDrawable(
    private val roundMode: RoundMode,
    @ColorInt backgroundColorInt: Int,
    @Px private val stroke: Float,
    @Px private val radius: Float,
    private val isSide: Boolean,
) : Drawable() {

    private val path = Path()
    private val rectF = RectF()
    private val halfStroke = stroke / 2f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColorInt
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        rectF.set(bounds)

        if (isSide) {
            when (roundMode) {
                RoundMode.TOP -> {
                    rectF.inset(halfStroke, halfStroke)
                    rectF.bottom = bounds.bottom.toFloat()
                }

                RoundMode.BOTTOM -> {
                    rectF.inset(halfStroke, halfStroke)
                    rectF.top = bounds.top.toFloat()
                }

                RoundMode.NONE -> {
                    rectF.inset(halfStroke, 0f)
                    rectF.top = bounds.top.toFloat()
                    rectF.bottom = bounds.bottom.toFloat()
                }

                else -> {
                    rectF.inset(halfStroke, halfStroke)
                }
            }
        } else {
            rectF.inset(halfStroke, halfStroke)
        }

        path.reset()
        val radii = roundMode.getRadiiForRoundMode(radius)

        if (roundMode == RoundMode.NONE) {
            canvas.drawRect(rectF, paint)
        } else {
            path.addRoundRect(rectF, radii, Path.Direction.CW)
            canvas.drawPath(path, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java", ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat"))
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}