package com.sergei.pokhodai.expensemanagement.feature.settings.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.TabContract
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.R
import kotlinx.serialization.Serializable

@Serializable
data object TabSettingsContract : TabContract {
    override val nameResId: Int = R.string.settings_tab
    override val iconResId: Int = R.drawable.ic_settings_selector
    override val startDestination = SettingsContract::class
}