package com.android.pokhodai.expensemanagement.ui.settings

import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.ui.settings.adapter.SettingAdapter
import com.android.pokhodai.expensemanagement.utils.enums.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    val settingsFlow = MutableStateFlow(
        Settings.values()
            .mapIndexed { index, settings ->
                SettingAdapter.Setting(
                    setting = settings,
                    isVisibleIcon = Settings.values().size - 1 != index
                )
            })
}