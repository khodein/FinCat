package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.router.ExchangeRateRouter
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.data.SettingsRepository
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.domain.model.SettingModel
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.mapper.SettingsMapper
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile.SettingProfileItem
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings.SettingItem
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdFlowUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val getUserSelfUseCase: GetUserSelfUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val settingsMapper: SettingsMapper,
    private val settingsRepository: SettingsRepository,
    private val localeManager: LocaleManager,
    private val userRouter: UserRouter,
    private val categoryRouter: CategoryRouter,
    private val exchangeRateRouter: ExchangeRateRouter,
) : ViewModel() {

    private var loadingJob: Job? = null
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    private var isFirstLoad: Boolean = false

    init {
        updateTop()
        viewModelScope.launch {
            localeManager.getLanguageFlow().collect {
                fetchData()
            }
        }

        viewModelScope.launch {
            getUserIdFlowUseCase.invoke().collect {
                fetchData()
            }
        }
    }

    private fun updateTop() {
        _topFlow.value = settingsMapper.getToolbarItemState()
    }

    private fun fetchData() {
        if (!isFirstLoad) {
            updateLoading()
            isFirstLoad = true
        }
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            loadUser()
        }
    }

    private suspend fun loadUser() {
        runCatching {
            getUserSelfUseCase.invoke()
        }.onSuccess { model ->
            loadSettingList(model)
        }.onFailure {
            updateError()
        }
    }

    private fun loadSettingList(userSelfModel: UserSelfModel) {
        runCatching {
            settingsRepository.getSettingList()
        }.onSuccess { list ->
            updateSuccess(
                list = list,
                userSelfModel = userSelfModel
            )
        }.onFailure {
            updateError()
        }
    }

    private fun updateLoading() {
        _requestFlow.value = RequestItem.State.Loading
        _itemsFlow.value = emptyList()
    }

    private fun updateError() {
        _requestFlow.value = settingsMapper.getRequestError(::fetchData)
        _itemsFlow.value = emptyList()
    }

    private fun updateSuccess(
        userSelfModel: UserSelfModel,
        list: List<SettingModel>
    ) {
        _requestFlow.value = RequestItem.State.Idle
        _itemsFlow.value = settingsMapper.getItems(
            list = list,
            userSelfModel = userSelfModel,
            onClickProfile = ::onClickProfile,
            onClickSetting = ::onClickSetting
        ).ifEmpty {
            _requestFlow.value = settingsMapper.getRequestEmpty()
            emptyList()
        }
    }

    private fun onClickProfile(state: SettingProfileItem.State) {
        val data = state.data
        if (data is UserSelfModel) {
            data.userId?.let(userRouter::goToUser)
        }
    }

    private fun onClickSetting(state: SettingItem.State) {
        val data = state.data
        if (data is SettingModel) {
            when (data) {
                SettingModel.MANAGER_CATEGORY -> categoryRouter.goToCategoryManager()
                SettingModel.LOGOUT -> userRouter.goToUserList()
                SettingModel.LANGUAGE -> userRouter.goToUserLanguage()
                SettingModel.VALUTE -> exchangeRateRouter.goToExchangeRate()
                SettingModel.FAQ -> {

                }
            }
        }
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}