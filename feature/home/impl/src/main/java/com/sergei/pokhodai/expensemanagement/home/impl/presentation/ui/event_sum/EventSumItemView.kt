package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_sum

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
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.feature.home.impl.databinding.ViewEventSumItemBinding

internal class EventSumItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<EventSumItem.State> {

    private val binding = ViewEventSumItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        applyPadding(P_8_8_8_8)

        ViewCorner.Border(
            radius = RADIUS.value,
            roundMode = RoundMode.ALL,
            borderType = BorderType.Simple(
                strokeWidth = STROKE_WIDTH.value,
                strokeColor = getColor(baseR.color.grey_300)
            )
        )
            .resolve(
                view = this,
                backgroundColorInt = getColor(baseR.color.background)
            )
    }

    override fun bindState(state: EventSumItem.State) {
        binding.eventSumExpenseText.text = state.expenseText
        binding.eventSumBalanceText.text = state.balanceText
        binding.eventSumIncomeText.text = state.incomeText
    }

    private companion object {
        val RADIUS = ViewDimension.Dp(8)
        val STROKE_WIDTH = ViewDimension.Dp(1)
    }
}