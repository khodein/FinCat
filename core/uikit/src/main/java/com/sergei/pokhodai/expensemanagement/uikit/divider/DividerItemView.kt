package com.sergei.pokhodai.expensemanagement.uikit.divider

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.google.android.material.divider.MaterialDivider
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView

class DividerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<DividerItem.State> {

    private val divider by lazy {
        MaterialDivider(context).apply {
            layoutParams = LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
        }
    }

    override fun bindState(state: DividerItem.State) {

    }

    private fun bindEmpty(state: DividerItem.State.Empty) {

    }

    private fun bindDividerHorizontal(state: DividerItem.State.Horizontal) {

    }
}