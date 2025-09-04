package com.sergei.pokhodai.expensemanagement.feature.settings.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.router.contract.TabSettingsContract
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

internal class SettingsTabProviderImpl : TabProvider {

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    override fun getDestination(): List<TabDestination> {
        val settingsTab = TabSettingsContract
        val clazzSettingsTab = settingsTab::class
        return listOf(
            TabDestination(
                order = 3,
                id = clazzSettingsTab.serializer().generateHashCode(),
                clazz = clazzSettingsTab,
                tab = settingsTab,
                isStart = false
            )
        )
    }
}