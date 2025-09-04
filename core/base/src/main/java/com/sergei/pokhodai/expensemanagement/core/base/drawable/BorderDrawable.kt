package com.sergei.pokhodai.expensemanagement.core.base.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.getRadiiForRoundMode

class BorderDrawable(
    private val roundMode: RoundMode,
    @ColorInt private val strokeColor: Int,
    @Px private val stroke: Float,
    @Px private val radius: Float,
    @Px private val dash: Float = 0f,
    @Px private val gap: Float = 0f,
    private val isSide: Boolean,
    isGapDash: Boolean = false,
) : Drawable() {

    private val rectF = RectF()
    private val path = Path()
    private val halfStroke = stroke / 2f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val borderPaint = if (isGapDash) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = strokeColor
            strokeWidth = stroke
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            paint.pathEffect = DashPathEffect(
                floatArrayOf(dash, gap),
                0f
            )
        }
    } else {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = strokeColor
            style = Paint.Style.STROKE
            strokeWidth = stroke
        }
    }

    override fun draw(canvas: Canvas) {
        rectF.set(bounds)

        val radii = roundMode.getRadiiForRoundMode(radius)

        if (!isSide) {
            rectF.inset(halfStroke, halfStroke)
            path.reset()

            if (roundMode == RoundMode.NONE) {
                canvas.drawRect(rectF, borderPaint)
            } else {
                path.addRoundRect(rectF, radii, Path.Direction.CW)
                canvas.drawPath(path, borderPaint)
            }
        } else {
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

            path.reset()

            when (roundMode) {
                RoundMode.ALL -> {
                    path.addRoundRect(rectF, radii, Path.Direction.CW)
                    canvas.drawPath(path, borderPaint)
                }

                RoundMode.TOP -> {
                    path.moveTo(rectF.left, rectF.bottom)
                    path.lineTo(rectF.left, rectF.top + radius)
                    path.arcTo(
                        rectF.left,
                        rectF.top,
                        rectF.left + 2 * radius,
                        rectF.top + 2 * radius,
                        180f,
                        90f,
                        false
                    )
                    path.lineTo(rectF.right - radius, rectF.top)
                    path.arcTo(
                        rectF.right - 2 * radius,
                        rectF.top,
                        rectF.right,
                        rectF.top + 2 * radius,
                        270f,
                        90f,
                        false
                    )
                    path.lineTo(rectF.right, rectF.bottom)

                    canvas.drawPath(path, borderPaint)
                }

                RoundMode.BOTTOM -> {
                    path.moveTo(rectF.left, rectF.top)
                    path.lineTo(rectF.left, rectF.bottom - radius)
                    path.arcTo(
                        rectF.left,
                        rectF.bottom - 2 * radius,
                        rectF.left + 2 * radius,
                        rectF.bottom,
                        180f,
                        -90f,
                        false
                    )
                    path.lineTo(rectF.right - radius, rectF.bottom)
                    path.arcTo(
                        rectF.right - 2 * radius,
                        rectF.bottom - 2 * radius,
                        rectF.right,
                        rectF.bottom,
                        90f,
                        -90f,
                        false
                    )
                    path.lineTo(rectF.right, rectF.top)

                    canvas.drawPath(path, borderPaint)
                }

                RoundMode.NONE -> {
                    canvas.drawLine(rectF.left, rectF.top, rectF.left, rectF.bottom, borderPaint)
                    canvas.drawLine(rectF.right, rectF.top, rectF.right, rectF.bottom, borderPaint)
                }
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        borderPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        borderPaint.colorFilter = colorFilter
    }

    @Deprecated("Deprecated in Java", ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat"))
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}