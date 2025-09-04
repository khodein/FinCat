package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.feature.home.impl.databinding.ViewEventItemBinding

internal class EventItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<EventItem.State> {
    private val binding = ViewEventItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: EventItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )
        maxHeight = MAX_HEIGHT.value

        makeRippleDrawable(
            rippleColorValue = ColorValue.Res(baseR.color.blue_50),
            topRightCornerRadius = RIPPLE_RADIUS,
            topLeftCornerRadius = RIPPLE_RADIUS,
            bottomLeftCornerRadius = RIPPLE_RADIUS,
            bottomRightCornerRadius = RIPPLE_RADIUS
        )

        applyPadding(P_8_8_8_8)

        setOnDebounceClick { state?.let { it.onClick?.invoke(it) } }
    }

    override fun bindState(state: EventItem.State) {
        this.state = state
        bindAmount(state.amount)
        bindName(state.name)
        binding.eventKind.bindState(state.categoryKindItemState)
    }

    private fun bindAmount(amount: CharSequence) {
        binding.eventAmount.text = amount
    }

    private fun bindName(name: CharSequence) {
        binding.eventName.text = name
    }

    private companion object {
        val MAX_HEIGHT = ViewDimension.Dp(56)
        val RIPPLE_RADIUS = ViewDimension.Dp(8)
    }
}