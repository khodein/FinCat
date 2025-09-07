package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.base.utils.isEmailValid
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
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
    private val userDataStoreRepository: UserDataStoreRepository,
    private val eventBus: EventBus,
    private val supportRouter: SupportRouter,
    private val router: Router,
    localeManager: LocaleManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    UserMapper.ItemListProvider {
    private val route = savedStateHandle.toRoute<UserRouterContract>()
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private var editableJob: Job? = null
    private var loadJob: Job? = null

    private val userId = route.userId
    private var userDataStoreId: Long? = null
    private val isEdit = userId != null
    private var isExitTap: Boolean = false

    private var userModel: UserSelfModel = UserSelfModel.getDefault()
    private var userErrorState: UserErrorState = UserErrorState(
        isEmailError = false,
        isNameError = false
    )
    private var isEmptyDataStoreUser = false

    private var localeLanguageModel: LocaleLanguageModel = localeManager.getLanguage()

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
            callback = ::onChangeCurrency
        )

        eventBus.subscribe(
            key = UserModule.Keys.USER_AVATAR,
            event = UserAvatarModel::class.java,
            callback = ::onChangeAvatar
        )

        updateToolbar()
        loadDataStoreUserId()
    }

    private fun loadDataStoreUserId() {
        updateBottom(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            runCatching {
                userDataStoreRepository.getUserId()
            }.onSuccess {
                userDataStoreId = it
                isEmptyDataStoreUser = it == null
                loadDataUserById()
            }
        }
    }

    private suspend fun loadDataUserById() {
        runCatching {
            userId?.let {
                userRepository.getUserById(it)
            } ?: UserSelfModel.getDefault().copy(
                currency = if (localeLanguageModel == LocaleLanguageModel.RU) {
                    UserCurrencyModel.RUB
                } else {
                    UserCurrencyModel.USD
                },
                avatar = UserAvatarModel.entries.random()
            )
        }.onSuccess { user ->
            userModel = user
            updateErrorState()
            updateSuccess()
        }.onFailure {
            supportRouter.showSnackBar(message = userMapper.getGlobalErrorText())
            updateBottom()
            router.pop()
        }
    }

    private fun loadSave() {
        updateBottom(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                if (isEdit) {
                    userRepository.setUpdateAndGetUser(userModel)
                } else {
                    userRepository.setAndGetUser(userModel)
                }
            }.onSuccess { model ->
                userModel = model
                if (isEmptyDataStoreUser || model.userId == userId) {
                    loadDataStoreUser(model.userId)
                } else {
                    updateSaveSuccess()
                }
            }.onFailure {
                supportRouter.showSnackBar(message = userMapper.getGlobalErrorText())
                updateBottom()
            }
        }
    }

    private suspend fun loadDataStoreUser(userId: Long?) {
        runCatching {
            userDataStoreRepository.setUserData(userId)
        }.onSuccess {
            updateSaveSuccess()
        }.onFailure {
            supportRouter.showSnackBar(message = userMapper.getGlobalErrorText())
            updateBottom()
        }
    }

    private fun updateSaveSuccess() {
        isEmptyDataStoreUser = false
        updateBottom()
        supportRouter.showSnackBar(message = userMapper.getSuccessSaveText())
        onBackPressed()
    }

    private fun updateToolbar() {
        _topFlow.value = userMapper.getToolbarItemState(
            isEmptyDataStoreUser = isEmptyDataStoreUser,
            onClickBack = ::onBackPressed
        )
    }

    fun onBackPressed() {
        if (isEmptyDataStoreUser) {
            if (!isExitTap) {
                supportRouter.showSnackBar(userMapper.getExitDataStoreEmptyText())
                isExitTap = true
            } else {
                supportRouter.exitApp()
            }
        } else {
            router.pop()
        }
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
            isEmptyDataStoreUser = isEmptyDataStoreUser,
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
        supportRouter.hideKeyboard()
        when {
            userErrorState.isEmailError -> {
                supportRouter.showSnackBar(userMapper.getEmailErrorText())
                return
            }
            userErrorState.isNameError -> {
                supportRouter.showSnackBar(userMapper.getNameErrorText())
                return
            }
        }

        loadSave()
    }

    override fun onChangeName(name: String) {
        onChangeDebounce {
            userModel = userModel.copy(name = name)
            updateErrorState()
            updateItems()
        }
    }

    override fun onChangeEmail(email: String) {
        onChangeDebounce {
            userModel = userModel.copy(email = email)
            updateErrorState()
            updateItems()
        }
    }

    private fun updateErrorState() {
        val emailTrim = userModel.email.trim()
        val nameTrim = userModel.name.trim()
        userErrorState = userErrorState.copy(
            isEmailError = if (emailTrim.isEmpty()) {
                false
            } else {
                !emailTrim.isEmailValid()
            },
            isNameError = nameTrim.isEmpty() || nameTrim.length < 2
        )
    }

    private fun onChangeAvatar(model: UserAvatarModel) {
        userModel = userModel.copy(avatar = model)
        updateItems()
    }

    override fun onClickTag(state: TagItem.State) {
        val data = state.data
        if (data is UserSettingState) {
            when (data) {
                UserSettingState.LANGUAGE -> userRouter.goToUserLanguage()
                UserSettingState.CURRENCY -> userModel.currency?.let(userRouter::goToUserCurrency)
            }
        }
    }

    override fun onClickEditAvatar() {
        userRouter.goToUserAvatar(userModel.avatar)
    }

    private fun onChangeCurrency(model: UserCurrencyModel) {
        userModel = userModel.copy(currency = model)
        updateSuccess()
    }

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(UserModule.Keys.USER_CURRENCY)
        eventBus.unsubscribe(UserModule.Keys.USER_AVATAR)
    }

    private fun onChangeDebounce(
        onChange: () -> Unit
    ) {
        editableJob?.cancel()
        editableJob = viewModelScope.launch {
            delay(EDITABLE_DEBOUNCE)
            onChange.invoke()
        }
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
        const val EDITABLE_DEBOUNCE = 300L
    }
}