package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.data.SettingsRepository
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.domain.model.SettingModel
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.mapper.SettingsMapper
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings.SettingItem
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val settingsMapper: SettingsMapper,
    private val settingsRepository: SettingsRepository,
    private val getUserSelfUseCase: GetUserSelfUseCase,
    private val localeManager: LocaleManager,
    private val userRouter: UserRouter,
) : ViewModel() {

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _appBarFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val appBarFlow = _appBarFlow.asStateFlow()

    private fun updateAppBar() {
        _appBarFlow.value = settingsMapper.getToolbarItemState()
    }

    init {
        viewModelScope.launch {
            localeManager.getLanguageFlow().collect {
                onStart()
            }
        }
    }

    fun onStart() {
        updateAppBar()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            runCatching {
                SettingsData(
                    list = settingsRepository.getSettingList(),
                    userSelfModel = getUserSelfUseCase.invoke()
                )
            }.onSuccess {
                _itemsFlow.value = settingsMapper.getItems(
                    list = it.list,
                    userSelfModel = it.userSelfModel,
                    onClickProfile = ::onClickProfile,
                    onClickSetting = ::onClickSetting
                )
            }
        }
    }

    private fun onClickProfile() {

    }

    private fun onClickSetting(state: SettingItem.State) {
        val data = state.data
        if (data is SettingModel) {
            when(data) {
                SettingModel.MANAGER_CATEGORY -> {

                }

                SettingModel.ASKED_QUESTION -> {

                }

                SettingModel.LOGOUT -> {

                }

                SettingModel.VALUTE -> {

                }

                SettingModel.LANGUAGE -> userRouter.goToUserLanguage()
            }
        }
    }

    private class SettingsData(
        val list: List<SettingModel>,
        val userSelfModel: UserSelfModel,
    )
}