package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.databinding.ViewFaqItemBinding

internal class FaqItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<FaqItem.State> {

    private val binding = ViewFaqItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: FaqItem.State? = null

    private val isExpanded: Boolean
        get() = state?.isExpanded ?: false

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        ViewCorner.Simple(
            radius = 12.dp,
            roundMode = RoundMode.ALL
        ).resolve(
            view = binding.faqItemContainer,
            backgroundColorInt = getColor(baseR.color.background)
        )

        binding.faqItemClick.setOnDebounceClick {
            state?.isExpanded = !isExpanded
            state?.let {
                it.onClickExpanded?.invoke(it)
            }
        }
    }

    override fun bindState(state: FaqItem.State) {
        this.state = state
        applyPadding(state.padding)
    }
}