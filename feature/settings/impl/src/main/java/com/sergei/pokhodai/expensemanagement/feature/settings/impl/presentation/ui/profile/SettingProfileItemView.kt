package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_16_16_24
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_24
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.databinding.ViewSettingProfileItemBinding

internal class SettingProfileItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<SettingProfileItem.State> {

    private val binding = ViewSettingProfileItemBinding.inflate(LayoutInflater.from(context), this)

    private var state: SettingProfileItem.State? = null

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        setBackgroundColor(getColor(R.color.grey_200))

        applyPadding(P_16_8_16_24)

        ViewCorner.Simple(
            radius = 24.dp,
            roundMode = RoundMode.ALL
        ).resolve(
            view = binding.settingsProfileAvatar,
            backgroundColorInt = getColor(R.color.background)
        )

        setOnDebounceClick { state?.onClick?.invoke() }
    }

    override fun bindState(state: SettingProfileItem.State) {
        this.state = state
        binding.settingsProfilePrefix.text = state.prefix
        binding.settingsProfileName.text = state.name
        binding.settingsProfileEmail.text = state.email
    }
}