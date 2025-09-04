package com.sergei.pokhodai.expensemanagement.uikit.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewHeaderItemBinding

class HeaderItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<HeaderItem.State> {

    private val binding = ViewHeaderItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        applyPadding(P_8_8_8_8)
    }

    override fun bindState(state: HeaderItem.State) {
        binding.headerSum.isVisible = state.sum?.let {
            binding.headerSum.text = it
            true
        } ?: false
        binding.headerTitle.text = state.title
    }
}