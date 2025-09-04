package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.UserModule
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.mapper.UserMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.state.UserErrorState
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.state.UserSettingState
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserRouterContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class UserViewModel(
    private val userRouter: UserRouter,
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val getCategoryDefaultListUseCase: GetCategoryDefaultListUseCase,
    private val setCategoriesUseCase: SetCategoriesUseCase,
    private val userDataStoreRepository: UserDataStoreRepository,
    private val eventBus: EventBus,
    localeManager: LocaleManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), UserMapper.ItemListProvider {

    private var loadJob: Job? = null
    private val route = savedStateHandle.toRoute<UserRouterContract>()
    private val userId = route.userId
    private val isEdit = userId != null

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private var userModel: UserSelfModel = UserSelfModel.getDefault()
    private var userErrorState: UserErrorState = UserErrorState(
        isEmailError = false,
        isNameError = false
    )
    private var isEmptyDataStoreUser = true
    private var localeLanguageModel: LocaleLanguageModel = localeManager.getLanguage()

    private fun getDefaultCurrency(): UserCurrencyModel {
        return if (localeLanguageModel == LocaleLanguageModel.RU) {
            UserCurrencyModel.RUB
        } else {
            UserCurrencyModel.USD
        }
    }

    init {
        viewModelScope.launch {
            localeManager.getLanguageFlow().collect { model ->
                localeLanguageModel = model
                updateSuccess()
            }
        }

        eventBus.subscribe(
            key = UserModule.Keys.USER_CURRENCY,
            event = UserCurrencyModel::class.java,
            callback = ::onEventCallback
        )

        updateToolbar()
        loadData()
    }

    private fun onEventCallback(model: UserCurrencyModel) {
        userModel = userModel.copy(currency = model)
        updateSuccess()
    }

    private fun loadData() {
        updateBottom(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            runCatching {
                val userByDataStore = userDataStoreRepository.getUserDataStore()
                val userById = userId?.let {
                    userRepository.getUserById(it)
                } ?: UserSelfModel.getDefault().copy(
                    currency = getDefaultCurrency()
                )

                userByDataStore to userById
            }.onSuccess { users ->
                isEmptyDataStoreUser = users.first.isEmpty()
                userModel = users.second
                updateSuccess()
            }.onFailure {
                updateBottom()
            }
        }
    }

    private fun loadSave() {
        if (userErrorState.isEmailError || userErrorState.isNameError) {
            updateItems()
            return
        }

        updateBottom(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                if (isEdit) {
                    userRepository.setUpdateUser(userModel)
                } else {
                    userRepository.setUser(userModel)
                }
            }.onSuccess {
                when {
                    isEmptyDataStoreUser -> loadDataStoreUser()
                    !isEdit -> loadDefaultCategories()
                    else -> {
                        updateBottom()
                    }
                }
            }.onFailure {
                updateBottom()
            }
        }
    }

    private fun loadDataStoreUser() {
        viewModelScope.launch {
            runCatching {
                userRepository.getUserList().firstOrNull()?.let { model ->
                    userDataStoreRepository.setUserDataStore(model)
                } ?: throw Throwable()
            }.onSuccess {
                loadDefaultCategories()
            }.onFailure {
                updateBottom()
            }
        }
    }

    private fun loadDefaultCategories() {
        viewModelScope.launch {
            runCatching {
                setCategoriesUseCase.invoke(*getCategoryDefaultListUseCase.invoke().toTypedArray())
            }.onSuccess {

            }.onFailure {
                updateBottom()
            }
        }
    }

    private fun updateToolbar() {
        _topFlow.value = userMapper.getToolbarItemState(
            isEmptyDataStoreUser = isEmptyDataStoreUser,
            onClickBack = ::onClickBack
        )
    }

    private fun onClickBack() {

    }

    private fun updateSuccess() {
        updateToolbar()
        updateBottom()
        updateItems()
    }

    private fun updateItems() {
        _itemsFlow.value = userMapper.getItems(
            userModel = userModel,
            language = localeLanguageModel,
            isEdit = isEdit,
            errorState = userErrorState,
            provider = this
        )
    }

    private fun updateBottom(isLoading: Boolean = false) {
        _bottomFlow.value = userMapper.getButtonItemState(
            isEdit = isEdit,
            isLoading = isLoading,
            onClick = ::onClickButton
        )
    }

    private fun onClickButton(state: ButtonItem.State) {
        loadSave()
    }

    override fun onChangeName(name: String) {

    }

    override fun onChangeEmail(email: String) {

    }

    override fun onClickTag(state: TagItem.State) {
        val data = state.data
        if (data is UserSettingState) {
            when (data) {
                UserSettingState.LANGUAGE -> userRouter.goToUserLanguage()
                UserSettingState.CURRENCY -> userRouter.goToUserCurrency(userModel.currency)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(UserModule.Keys.USER_CURRENCY)
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}