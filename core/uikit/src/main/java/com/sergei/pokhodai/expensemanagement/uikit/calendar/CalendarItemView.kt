package com.sergei.pokhodai.expensemanagement.uikit.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_8_0_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewCalendarItemBinding

class CalendarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<CalendarItem.State> {

    private var state: CalendarItem.State? = null
    private val binding = ViewCalendarItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT,
        )

        applyPadding(P_0_8_0_8)

        val rippleClickColor = ColorValue.Res(R.color.blue_50)

        binding.calendarLeft.run {
            makeRippleDrawable(
                rippleColorValue = rippleClickColor,
                topRightCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                topLeftCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                bottomLeftCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                bottomRightCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
            )

            setOnDebounceClick {
                state?.onClickLeft?.invoke()
            }
        }

        binding.calendarItem.run {
            ViewCorner.Simple(
                radius = EVENT_CALENDAR_RADIUS.value,
                roundMode = RoundMode.ALL
            ).resolve(
                view = this,
                backgroundColorInt = getColor(R.color.grey_100)
            )

            makeRippleDrawable(
                rippleColorValue = rippleClickColor,
                topRightCornerRadius = EVENT_CALENDAR_RADIUS,
                topLeftCornerRadius = EVENT_CALENDAR_RADIUS,
                bottomLeftCornerRadius = EVENT_CALENDAR_RADIUS,
                bottomRightCornerRadius = EVENT_CALENDAR_RADIUS,
            )

            setOnDebounceClick {
                state?.onClickCalendar?.invoke()
            }
        }

        binding.calendarRight.run {
            makeRippleDrawable(
                rippleColorValue = rippleClickColor,
                topRightCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                topLeftCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                bottomLeftCornerRadius = EVENT_CLICK_RIPPLE_RADIUS,
                bottomRightCornerRadius = EVENT_CLICK_RIPPLE_RADIUS
            )

            setOnDebounceClick {
                state?.onClickRight?.invoke()
            }
        }
    }

    override fun bindState(state: CalendarItem.State) {
        this.state = state
        binding.calendarText.text = state.text
    }

    private companion object {
        val EVENT_CLICK_RIPPLE_RADIUS = ViewDimension.Dp(20)
        val EVENT_CALENDAR_RADIUS = ViewDimension.Dp(50)
    }
}