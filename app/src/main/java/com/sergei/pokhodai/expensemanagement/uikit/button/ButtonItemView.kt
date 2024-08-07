package com.sergei.pokhodai.expensemanagement.uikit.button

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.ViewButtonItemBinding
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.utils.applyMargin
import com.sergei.pokhodai.expensemanagement.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.utils.getColor
import com.sergei.pokhodai.expensemanagement.utils.getColorStateList
import com.sergei.pokhodai.expensemanagement.utils.setAppearance
import com.sergei.pokhodai.expensemanagement.utils.toPx

class ButtonItemView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ButtonItem.View {

    private var state: ButtonItem.State? = null

    private val binding = ViewButtonItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.buttonItemCard.setOnThrottleClickListener {
            if (state?.isEnabled == true) {
                state?.onClick?.invoke()
            }
        }
    }

    override fun bindState(state: ButtonItem.State) {
        this.state = state
        setRootState(state)
        binding.buttonItemCard.updateLayoutParams<LayoutParams> {
            this.height = state.size.height.value
            this.width = state.size.width.value
        }
        setLeading(state)
        setStyleState(state)
        binding.buttonItemText.text = state.text
    }

    private fun setLeading(state: ButtonItem.State) = with(binding) {
        buttonItemImg.isVisible = if (state.style.isLeading) {
            buttonItemImg.setImageResource(state.style.leadingDrawableRes)
            buttonItemText.applyPadding(left = 5.dp.toPx)
            buttonItemImg.imageTintList = if (state.isEnabled) {
                getColorStateList(R.color.grey_500)
            } else {
                null
            }
            true
        } else {
            false
        }
    }

    private fun setStyleState(state: ButtonItem.State) = with(binding) {
        if (state.style is ButtonItem.Style.Outlined) {
            val strokeColorRes = if (state.isEnabled) state.style.strokeColorRes else R.color.grey_400
            buttonItemCard.setStrokeColor(getColorStateList(strokeColorRes))
            buttonItemCard.strokeWidth = state.style.strokeWidth.value
        } else {
            buttonItemCard.setStrokeColor(ColorStateList.valueOf(Color.TRANSPARENT))
            buttonItemCard.strokeWidth = 0
        }

        val backgroundColorRes = if (state.isEnabled) state.style.backgroundColorRes else R.color.grey_300
        buttonItemCard.setCardBackgroundColor(getColor(backgroundColorRes))
        buttonItemCard.radius = state.cornerRadius.value.toFloat()
        buttonItemText.setAppearance(state.style.textStyleRes)

        if (state.isEnabled) {
            buttonItemText.setTextColor(getColor(R.color.grey_400))
        }
    }

    private fun setRootState(state: ButtonItem.State) = with(binding) {
        root.applyMargin(
            left = state.margins.left.toPx,
            right = state.margins.right.toPx,
            top = state.margins.top.toPx,
            bottom = state.margins.bottom.toPx
        )
        root.setBackgroundResource(state.containerBackgroundRes)
        root.applyPadding(
            left = state.containerPadding.left.toPx,
            right = state.containerPadding.right.toPx,
            top = state.containerPadding.top.toPx,
            bottom = state.containerPadding.bottom.toPx
        )
    }
}