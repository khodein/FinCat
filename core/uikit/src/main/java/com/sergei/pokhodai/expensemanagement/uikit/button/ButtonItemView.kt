package com.sergei.pokhodai.expensemanagement.uikit.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageColorFilterValue
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.resolveToLayoutParams
import com.sergei.pokhodai.expensemanagement.core.base.utils.setColorFilter
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.databinding.ViewButtonItemBinding

class ButtonItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<ButtonItem.State> {

    private val binding = ViewButtonItemBinding.inflate(LayoutInflater.from(context), this)
    private var state: ButtonItem.State? = null

    init {
        layoutParams = LayoutParams(
            WRAP_CONTENT,
            WRAP_CONTENT,
        )

        binding.viewButtonItemContent.setOnDebounceClick {
            state?.let {
                if (!it.isLoading) {
                    it.onClick?.invoke(it)
                }
            }
        }
    }

    override fun bindState(state: ButtonItem.State) {
        this.state = state

        binding.viewButtonItemContent.resolveToLayoutParams(
            width = state.width,
            height = state.height,
        )
        binding.viewButtonItemContent.minWidth = ViewDimension.Dp(130).value
        binding.viewButtonItemContent.minHeight = ViewDimension.Dp(32).value

        setBackgroundColor(state.container.backgroundColor.getColor(context))
        applyPadding(state.container.paddings)

        bindText(state.value)
        bindFill(
            isEnabled = state.isEnabled,
            fill = state.fill,
            radius = state.radius
        )
        bindIcon(state.icon)
        bindLoading(state.isLoading, state.icon)
    }

    private fun bindLoading(isLoading: Boolean, icon: ImageValue?) {
        binding.viewButtonItemFlow.isInvisible= isLoading
        binding.viewButtonItemText.isInvisible = isLoading
        if (icon != null) {
            binding.viewButtonItemIcon.isInvisible = isLoading
        } else {
            binding.viewButtonItemIcon.isVisible = false
        }
        binding.viewButtonItemLoading.isVisible = isLoading
    }

    private fun bindFill(
        isEnabled: Boolean,
        radius: ViewDimension.Dp,
        fill: ButtonItem.Fill
    ) {
        when (fill) {
            is ButtonItem.Fill.Filled -> bindFilled(
                fill = fill,
                radius = radius,
                isEnabled = isEnabled,
            )

            is ButtonItem.Fill.Outline -> bindOutline(
                fill = fill,
                radius = radius,
                isEnabled = isEnabled,
            )

            is ButtonItem.Fill.Text -> bindText(
                fill = fill,
                radius = radius,
                isEnabled = isEnabled
            )
        }
    }

    private fun bindText(
        fill: ButtonItem.Fill.Text,
        radius: ViewDimension.Dp,
        isEnabled: Boolean,
    ) = with(binding.viewButtonItemContent) {
        ViewCorner.Default()
            .resolve(
                view = this,
                backgroundColorInt = if (isEnabled) {
                    fill.backgroundColor.getColor(context)
                } else {
                    getColor(R.color.grey_300)
                }
            )

        this.makeRipple(
            radius = radius,
            color = fill.rippleColor,
            isEnabled = isEnabled
        )

        setIconFilter(isEnabled = isEnabled, color = fill.iconColor)
        setTextColor(isEnabled = isEnabled, color = fill.textColor)
        setIndicatorColor(color = fill.indicatorColor)
    }

    private fun bindOutline(
        fill: ButtonItem.Fill.Outline,
        radius: ViewDimension.Dp,
        isEnabled: Boolean,
    ) = with(binding.viewButtonItemContent) {
        val backgroundColor = if (isEnabled) {
            fill.backgroundColor.getColor(context)
        } else {
            getColor(R.color.white)
        }

        val strokeColor = if (isEnabled) {
            fill.borderColor.getColor(context)
        } else {
            getColor(R.color.grey_400)
        }

        ViewCorner.Border(
            radius = radius.value,
            roundMode = RoundMode.ALL,
            borderType = BorderType.Simple(
                strokeColor = strokeColor,
                strokeWidth = ViewDimension.Dp(1).value
            )
        ).resolve(
            view = this,
            backgroundColorInt = backgroundColor
        )

        this.makeRipple(
            radius = radius,
            color = fill.rippleColor,
            isEnabled = isEnabled
        )

        setIconFilter(isEnabled = isEnabled, color = fill.iconColor)
        setTextColor(isEnabled = isEnabled, color = fill.textColor)
        setIndicatorColor(color = fill.indicatorColor)
    }

    private fun bindFilled(
        fill: ButtonItem.Fill.Filled,
        radius: ViewDimension.Dp,
        isEnabled: Boolean,
    ) = with(binding.viewButtonItemContent) {
        val backgroundColor = if (isEnabled) {
            fill.backgroundColor.getColor(context)
        } else {
            getColor(R.color.white)
        }

        ViewCorner.Simple(
            radius = radius.value,
            roundMode = RoundMode.ALL
        ).resolve(
            view = this,
            backgroundColorInt = backgroundColor
        )

        this.makeRipple(
            radius = radius,
            color = fill.rippleColor,
            isEnabled = isEnabled
        )

        setIconFilter(isEnabled = isEnabled, color = fill.iconColor)
        setTextColor(isEnabled = isEnabled, color = fill.textColor)
        setIndicatorColor(color = fill.indicatorColor)
    }

    private fun bindIcon(value: ImageValue?) {
        binding.viewButtonItemIcon.bindStateOptional(
            state = value,
            binder = { this.load(it) }
        )
    }

    private fun View.makeRipple(
        radius: ViewDimension.Dp,
        color: ColorValue,
        isEnabled: Boolean,
    ) {
        if (isEnabled) {
            this.makeRippleDrawable(
                rippleColorValue = color,
                topLeftCornerRadius = radius,
                topRightCornerRadius = radius,
                bottomLeftCornerRadius = radius,
                bottomRightCornerRadius = radius
            )
        } else {
            foreground = null
        }
    }

    private fun setIconFilter(
        isEnabled: Boolean,
        color: ColorValue
    ) {
        val colorFilter = if (isEnabled) {
            ImageColorFilterValue.Tint(tintColor = color)
        } else {
            ImageColorFilterValue.Disabled()
        }
        binding.viewButtonItemIcon.setColorFilter(colorFilter)
    }

    private fun setTextColor(
        isEnabled: Boolean,
        color: ColorValue,
    ) {
        val textColor = if (isEnabled) {
            color.getColor(context)
        } else {
            getColor(R.color.grey_400)
        }

        binding.viewButtonItemText.setTextColor(textColor)
    }

    private fun setIndicatorColor(
        color: ColorValue
    ) {
        binding.viewButtonItemLoading.setIndicatorColor(color.getColor(context))
    }

    private fun bindText(value: String) {
        binding.viewButtonItemText.text = value
    }
}