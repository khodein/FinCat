package com.sergei.pokhodai.expensemanagement.uikit.text

import android.content.Context
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.core.view.updateLayoutParams
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.utils.ViewDimension
import com.sergei.pokhodai.expensemanagement.utils.applyMargin
import com.sergei.pokhodai.expensemanagement.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.utils.getColor
import com.sergei.pokhodai.expensemanagement.utils.setAppearance
import com.sergei.pokhodai.expensemanagement.utils.toPx

class TextItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)  : AppCompatTextView(context, attrs, defStyleAttr), TextItem.View {

    private var state: TextItem.State? = null

    init {
        layoutParams = ViewGroup.LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT
        )

        setOnThrottleClickListener {
            state?.onClick?.invoke()
        }
    }

    override fun bindState(state: TextItem.State) {
        updateLayoutParams {
            this.height = state.size.height.value
            this.width = state.size.width.value
        }

        gravity = state.gravity

        setBackgroundResource(state.backgroundRes)

        applyMargin(
            left = state.margins.left.toPx,
            right = state.margins.right.toPx,
            top = state.margins.top.toPx,
            bottom = state.margins.bottom.toPx
        )

        applyPadding(
            left = state.paddings.left.toPx,
            right = state.paddings.right.toPx,
            top = state.paddings.top.toPx,
            bottom = state.paddings.bottom.toPx
        )

        state.textColorInt?.let {
            setTextColor(it)
        } ?: run {
            setTextColor(null)
        }

        state.textAppearance?.let(::setAppearance)

        text = state.text
    }
}

