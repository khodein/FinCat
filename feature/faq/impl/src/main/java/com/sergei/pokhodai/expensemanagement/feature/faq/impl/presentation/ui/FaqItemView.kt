package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui

import android.content.Context
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
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

    private val imageExpandRotation: Float
        get() = if (isExpanded) {
            EXPANDED_ROTATION
        } else {
            EXPANDED_COLLAPSE
        }

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        ViewCorner.Simple(
            radius = 8.dp,
            roundMode = RoundMode.ALL
        ).resolve(
            view = binding.faqItemContainer,
            backgroundColorInt = getColor(baseR.color.background)
        )

        binding.faqItemClick.setOnDebounceClick {
            state?.isExpanded = !isExpanded
            binding.faqItemExpandedDescription.setExpanded(
                expand = isExpanded,
                animate = true
            )
            binding.faqItemExpanded
                .animate()
                .rotation(imageExpandRotation)
                .setDuration(200)
                .start()
        }
    }

    override fun bindState(state: FaqItem.State) {
        this.state = state
        applyPadding(state.padding)
        binding.faqItemDescription.text = state.description
        binding.faqItemTitle.text = state.title
        binding.faqItemExpandedDescription.setExpanded(
            expand = isExpanded,
            animate = false
        )
        binding.faqItemExpanded.rotation = imageExpandRotation
    }

    private companion object {
        const val EXPANDED_ROTATION = 180f
        const val EXPANDED_COLLAPSE = 0f
    }
}