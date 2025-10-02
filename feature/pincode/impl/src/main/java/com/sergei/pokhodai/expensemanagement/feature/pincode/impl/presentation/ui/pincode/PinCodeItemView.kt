package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.pincode

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

internal class PinCodeItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), PinCodeItem.View {

    init {

    }
    override fun bindState(state: PinCodeItem.State) {
        TODO("Not yet implemented")
    }
}