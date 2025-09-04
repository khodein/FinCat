package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.report

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.feature.report.impl.databinding.ViewReportItemBinding

internal class ReportItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<ReportItem.State> {

    private val binding = ViewReportItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        applyPadding(P_16_8_16_8)
    }

    override fun bindState(state: ReportItem.State) {
        binding.reportKindItem.bindState(state.categoryKindItemState)
        binding.reportKindName.text = state.title
        binding.reportKindCount.text = state.subTitle
        binding.reportKindAmount.text = state.amount
        binding.reportKindPercent.text = state.percent
    }
}