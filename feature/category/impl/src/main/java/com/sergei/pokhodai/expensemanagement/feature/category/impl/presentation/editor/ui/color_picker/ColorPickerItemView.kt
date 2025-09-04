package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.color_picker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.ViewColorPickerItemBinding

internal class ColorPickerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<ColorPickerItem.State> {

    private val binding = ViewColorPickerItemBinding.inflate(LayoutInflater.from(context), this)

    private var state: ColorPickerItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        ViewCorner.Simple(
            radius = ViewDimension.Dp(12).value,
            roundMode = RoundMode.ALL,
        ).resolve(
            view = binding.viewColorPickerContainer,
            backgroundColorInt = getColor(R.color.surface_normal)
        )

        binding.viewColorPickerContainer.setOnDebounceClick { state?.onClick?.invoke() }
    }

    override fun bindState(state: ColorPickerItem.State) {
        this.state = state
        bindContainer(state.container)

        ViewCorner.Simple(
            radius = ViewDimension.Dp(12).value,
            roundMode = RoundMode.ALL,
        ).resolve(
            view = binding.viewColorPickerItem,
            backgroundColorInt = state.color.getColor(context)
        )

        binding.viewColorPickerName.text = state.name
    }

    private fun bindContainer(container: ColorPickerItem.Container) {
        applyPadding(container.paddings)
        setBackgroundColor(container.backgroundColor.getColor(context))
    }
}