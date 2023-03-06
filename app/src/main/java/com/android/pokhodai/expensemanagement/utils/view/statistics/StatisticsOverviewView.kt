package com.android.pokhodai.expensemanagement.utils.view.statistics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.ui.report.adapter.ReportAdapter
import com.android.pokhodai.expensemanagement.utils.dp
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import kotlin.math.ceil
import kotlin.math.roundToInt

class StatisticsOverviewView : View {

    private val heightView = 36.dp
    private val radius = 8.dp

    private val colorGroceries = ContextCompat.getColor(context, R.color.icons_C8E6C9)
    private val colorCafe = ContextCompat.getColor(context, R.color.icons_FFECB3)
    private val colorElectronics = ContextCompat.getColor(context, R.color.icons_FFCDD2)
    private val colorGifts = ContextCompat.getColor(context, R.color.icons_E1BEE7)
    private val colorLaundry = ContextCompat.getColor(context, R.color.icons_B3E5FC)
    private val colorParty = ContextCompat.getColor(context, R.color.icons_BBDEFB)
    private val colorLiquor = ContextCompat.getColor(context, R.color.icons_DCEDC8)
    private val colorFuel = ContextCompat.getColor(context, R.color.icons_D7CCC8)
    private val colorMaintenance = ContextCompat.getColor(context, R.color.icons_B39DDB)
    private val colorEducation = colorGroceries
    private val colorSelfDevelopment = ContextCompat.getColor(context, R.color.icons_CFD8DC)
    private val colorMoney = ContextCompat.getColor(context, R.color.icons_FFCCBC)
    private val colorHealth = ContextCompat.getColor(context, R.color.icons_F8BBD0)
    private val colorTransportation = ContextCompat.getColor(context, R.color.icons_B2EBF2)
    private val colorRestaurant = ContextCompat.getColor(context, R.color.icons_C5CAE9)
    private val colorSport = ContextCompat.getColor(context, R.color.icons_E6EE9C)
    private val colorSavings = ContextCompat.getColor(context, R.color.icons_FFECB3)
    private val colorInstitute = ContextCompat.getColor(context, R.color.icons_FFE0B2)
    private val colorAllowance = colorGroceries
    private val colorDonate = ContextCompat.getColor(context, R.color.icons_FFF9C4)
    private val colorTransparent = ContextCompat.getColor(context, R.color.transparent)

    private fun getColor(icons: Icons): Int {
        return when (icons) {
            Icons.GROCERIES -> colorGroceries
            Icons.CAFE -> colorCafe
            Icons.ELECTRONICS -> colorElectronics
            Icons.GIFTS -> colorGifts
            Icons.LAUNDRY -> colorLaundry
            Icons.PARTY -> colorParty
            Icons.LIQUOR -> colorLiquor
            Icons.FUEL -> colorFuel
            Icons.MAINTENANCE -> colorMaintenance
            Icons.EDUCATION -> colorEducation
            Icons.SELF_DEVELOPMENT -> colorSelfDevelopment
            Icons.MONEY -> colorMoney
            Icons.HEALTH -> colorHealth
            Icons.TRANSPORTATION -> colorTransportation
            Icons.RESTAURANT -> colorRestaurant
            Icons.SPORT -> colorSport
            Icons.SAVINGS -> colorSavings
            Icons.INSTITUTE -> colorInstitute
            Icons.ALLOWANCE -> colorAllowance
            Icons.DONATE -> colorDonate
            Icons.NONE -> colorTransparent
        }
    }

    private val backgroundPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = getColor(Icons.NONE)
        isAntiAlias = true
    }

    var reportWallets: List<ReportAdapter.ItemReport.ReportTypesWallet>? = null
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthDisplay = resources.displayMetrics.widthPixels

        val width: Int = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            else -> ceil(widthDisplay.toDouble()).toInt()
        }

        val height: Int = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            else -> heightView.roundToInt()
        }
        setMeasuredDimension(width, height)
    }

    private val rectF: RectF = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (reportWallets?.isEmpty() == true) {
            return
        }

        val indent = (reportWallets?.get(0)?.percent?.times(width)?.div(100) ?: 0f)
        val size = reportWallets?.size ?: 0
        val lastIndex = size.minus(1)
        reportWallets?.forEachIndexed { index, item ->
            backgroundPaint.color = getColor(item.reportWallet.icon)

            val left = if (index != 0) rectF.left + (reportWallets?.get(index-1)?.percent?.times(width)?.div(100) ?: 0f) else 0f
            val top = rectF.top
            val right = rectF.right + item.percent.times(width).div(100)
            val bottom = rectF.bottom

            when  {
                size == 1 -> {
                    rectF.set(
                        0f,
                        0f,
                        indent,
                        height.toFloat()
                    )

                    canvas?.drawRoundRect(rectF, radius, radius, backgroundPaint)
                }
                index == 0 -> {
                    rectF.set(
                        0f,
                        0f,
                        indent,
                        height.toFloat()
                    )
                    canvas?.drawStartEndRoundRect(
                        rect = rectF,
                        paint = backgroundPaint,
                        radius = radius,
                        isStart = true
                    )
                }
                index == lastIndex -> {
                    rectF.set(
                        left,
                        top,
                        right,
                        bottom
                    )

                    canvas?.drawStartEndRoundRect(
                        rect = rectF,
                        paint = backgroundPaint,
                        radius = radius,
                        isStart = false
                    )
                }
                else -> {
                    rectF.set(
                        left,
                        top,
                        right,
                        bottom
                    )

                    canvas?.drawRect(
                        rectF,
                        backgroundPaint
                    )
                }
            }
        }
    }

    private fun Canvas?.drawStartEndRoundRect(rect: RectF, paint: Paint, radius: Float, isStart: Boolean) {
        this?.drawRoundRect(rect, radius, radius, paint)
        this?.drawRect(
            if (isStart) rect.left + radius else rect.left,
            rect.top,
            if (isStart) rect.right else rect.right - radius,
            rect.bottom,
            paint
        )
    }
}