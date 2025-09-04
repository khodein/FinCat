package com.sergei.pokhodai.expensemanagement.feature.settings.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.data.SettingsRepository
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.SettingsViewModel
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.mapper.SettingsMapper
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile.SettingProfileItem
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile.SettingProfileItemView
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings.SettingItem
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings.SettingItemView
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.router.SettingsRouterProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.router.SettingsTabProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object SettingsModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = SettingItem.State::class.java, onView = ::SettingItemView)
            .add(clazz = SettingProfileItem.State::class.java, onView = ::SettingProfileItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::SettingsRouterProviderImpl) bind RouteProvider::class
            singleOf(::SettingsTabProviderImpl) bind TabProvider::class

            singleOf(::SettingsRepository)
            singleOf(::SettingsMapper)
            viewModelOf(::SettingsViewModel)
        }
    }
}