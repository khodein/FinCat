package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRippleDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.databinding.ViewSettingItemBinding

internal class SettingItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<SettingItem.State> {

    private val binding = ViewSettingItemBinding.inflate(LayoutInflater.from(context), this)

    private var state: SettingItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            48.dp
        )

        applyPadding(P_16_0_16_0)

        makeRippleDrawable(rippleColorValue = ColorValue.Res(R.color.blue_50))

        setOnDebounceClick { state?.let { it.onClick?.invoke(it) } }
    }

    override fun bindState(state: SettingItem.State) {
        this.state = state
        binding.settingTrailingIcon.bindStateOptional(
            state = state.trailingIcon,
            binder = {
                binding.settingTrailingIcon.load(it)
            }
        )
        binding.settingLeadingIcon.load(state.leadingIcon)
        binding.settingName.text = state.name
    }
}