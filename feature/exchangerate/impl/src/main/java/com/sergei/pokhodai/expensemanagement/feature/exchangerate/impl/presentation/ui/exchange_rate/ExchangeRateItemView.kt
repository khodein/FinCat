package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ui.exchange_rate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.utils.setAppearance
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.databinding.ViewExchangeRateItemBinding

internal class ExchangeRateItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<ExchangeRateItem.State> {

    private val binding = ViewExchangeRateItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )
    }

    override fun bindState(state: ExchangeRateItem.State) {
        binding.exchangeRateRate.text = state.rate
        binding.exchangeRateName.text = state.name
        binding.exchangeRateNominal.text = state.nominal

        if (state.isHeader) {
            binding.exchangeRateRate.setAppearance(baseR.style.Bold_14)
            binding.exchangeRateName.setAppearance(baseR.style.Bold_14)
            binding.exchangeRateNominal.setAppearance(baseR.style.Bold_14)
        } else {
            binding.exchangeRateRate.setAppearance(baseR.style.Regular_14)
            binding.exchangeRateName.setAppearance(baseR.style.Regular_14)
            binding.exchangeRateNominal.setAppearance(baseR.style.Regular_14)
        }
    }
}