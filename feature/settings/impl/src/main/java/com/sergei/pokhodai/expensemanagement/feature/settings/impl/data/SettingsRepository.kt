package com.sergei.pokhodai.expensemanagement.feature.settings.impl.data

import com.sergei.pokhodai.expensemanagement.feature.settings.impl.domain.model.SettingModel

internal class SettingsRepository {

    fun getSettingList(): List<SettingModel> {
        return SettingModel.entries
    }
}