package com.sergei.pokhodai.expensemanagement.uikit.spacer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.utils.applyPadding

class SpacerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), SpacerItem.View {

    override fun bindState(state: SpacerItem.State) {
        applyPadding(
            left = 0,
            right = 0,
            top = 0,
            bottom = state.padding.value
        )
    }
}