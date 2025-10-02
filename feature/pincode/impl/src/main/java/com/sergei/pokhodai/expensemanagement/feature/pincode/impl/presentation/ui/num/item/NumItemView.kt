package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num.item

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView

internal class NumItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<NumItem.State> {

    override fun bindState(state: NumItem.State) {

    }
}