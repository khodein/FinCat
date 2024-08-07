package com.sergei.pokhodai.expensemanagement.utils.view.avatar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.sergei.pokhodai.expensemanagement.data.models.User
import com.sergei.pokhodai.expensemanagement.R
import kotlin.math.min

class AvatarView : View {

    private var avatarRadius: Float = 0f

    private val colors = resources.getIntArray(R.array.colorsAvatar)

    private val backgroundPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
        isAntiAlias = true
    }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        color = Color.WHITE
    }

    var user: User? = null
        set(value) {
            field = value
            value?.let {
                textPlaceHolder = it.fullName()
            }
            invalidate()
        }

    private var textPlaceHolder = ""
        set(value) {
            field = value.take(1).uppercase()
            backgroundPaint.color = colors[field.hashCode() % colors.size]
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateComponents(w, h)
    }

    private fun updateComponents(w: Int, h: Int) {
        avatarRadius = min(w, h) / 2f
        textPaint.textSize = avatarRadius * 0.8F
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val halfWidth = width / 2f
        val halfHeight = height / 2f
        val posX = halfHeight - (textPaint.descent() + textPaint.ascent())/2f

        canvas?.drawCircle (halfWidth, halfHeight, avatarRadius, backgroundPaint)
        canvas?.drawText(textPlaceHolder, halfWidth, posX, textPaint)
    }
}