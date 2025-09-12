package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_12_16_12
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.ViewCategoryManagerItemBinding

internal class CategoryManagerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<CategoryManagerItem.State> {

    private val binding = ViewCategoryManagerItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: CategoryManagerItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        applyPadding(P_16_8_16_8)

        val rippleColorValue = ColorValue.Res(baseR.color.blue_50)

        binding.categoryManagerItemEdit.makeRippleDrawable(rippleColorValue = rippleColorValue)
        binding.categoryManagerClick.makeRippleDrawable(rippleColorValue = rippleColorValue)

        binding.categoryManagerItemEdit.setOnDebounceClick {
            state?.let { it.onClickEdit?.invoke(it) }
        }

        binding.categoryManagerItemDelete.setOnDebounceClick {
            state?.let { it.onClickDelete?.invoke(it) }
        }
    }

    override fun bindState(state: CategoryManagerItem.State) {
        this.state = state
        binding.categoryManagerItemDelete.isVisible = state.onClickDelete != null
        binding.categoryManagerItemName.text = state.name
        binding.categoryManagerItemBudget.text = state.budgetName
        binding.categoryManagerItemKing.bindState(state.categoryKindItemState)
    }
}