package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getScreenWidth

internal class StatisticItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), RecyclerItemView<StatisticItem.State> {

    private var state: StatisticItem.State? = null
    private var drawList = listOf<DrawCell>()
    private val statisticHeight = 36.dp
    private val statisticWidth: Int
        get() = resources.getScreenWidth()
    private val horizontalPaddings = 16.dp
    private val radius = 8.dp.toFloat()
    private val path = Path()

    private val leftRadii = floatArrayOf(
        radius, radius,
        0f, 0f,
        0f, 0f,
        radius, radius
    )

    private val rightRadii = floatArrayOf(
        0f, 0f,
        radius, radius,
        radius, radius,
        0f, 0f
    )

    private val zeroRadii = floatArrayOf(
        0f, 0f,
        0f, 0f,
        0f, 0f,
        0f, 0f
    )

    private val allRadii =  floatArrayOf(
        radius, radius,
        radius, radius,
        radius, radius,
        radius, radius,
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            else -> statisticWidth
        }

        val height: Int = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            else -> statisticHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun bindState(state: StatisticItem.State) {
        this.state = state

        val area = statisticWidth - (horizontalPaddings * 2)
        var left: Float = horizontalPaddings.toFloat()
        var right: Float = left
        val bottom: Float = statisticHeight.toFloat()

        drawList = state.cells.mapIndexed { index, cell ->
            left = right
            right = left + ((cell.percent * area) / 100)

            val radii = when {
                state.cells.size == 1 -> allRadii
                index == 0 -> leftRadii
                index == state.cells.lastIndex -> rightRadii
                else -> zeroRadii
            }

            val rectF = RectF(
                left,
                0f,
                right,
                bottom,
            )

            val paint = Paint().apply {
                isAntiAlias = true
                color = cell.color.getColor(context)
            }

            DrawCell(
                rectF = rectF,
                paint = paint,
                radii = radii
            )
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawList.forEach { cell ->
            path.reset()
            path.addRoundRect(cell.rectF, cell.radii, Path.Direction.CW)
            canvas.drawPath(path, cell.paint)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        requestLayout()
        state?.let(::bindState)
    }

    private class DrawCell(
        val rectF: RectF,
        val paint: Paint,
        val radii: FloatArray,
    )
}