package com.android.pokhodai.expensemanagement.ui.settings.adapter

import androidx.core.view.isVisible
import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.databinding.ItemSettingBinding
import com.android.pokhodai.expensemanagement.utils.enums.Settings
import javax.inject.Inject

class SettingAdapter @Inject constructor(): BaseListAdapter<SettingAdapter.Setting>() {

    private var onClickActionListener: ((Settings) -> Unit)? = null

    fun setOnClickActionListener(action: (Settings) -> Unit) {
        onClickActionListener = action
    }

    override fun build() {
        baseViewHolder(Setting::class, ItemSettingBinding::inflate) {item ->
            binding.run {
                txtNameSetting.text = root.context.getString(item.setting.textResId)
                ivSetting.setImageResource(item.setting.iconResId)
                ivSetting.isVisible = item.isVisibleIcon
                root.setOnClickListener {
                    onClickActionListener?.invoke(item.setting)
                }
            }
        }
    }


    data class Setting(
        val setting: Settings,
        val isVisibleIcon: Boolean
    )
}