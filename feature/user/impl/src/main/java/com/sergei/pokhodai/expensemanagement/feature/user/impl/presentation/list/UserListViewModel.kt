package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.DeleteAllCategoryUseCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.DeleteAllCategoryUserCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserIdRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list.mapper.UserListMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user.UserListItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class UserListViewModel(
    private val userRepository: UserRepository,
    private val userDataStoreRepository: UserIdRepository,
    private val userListMapper: UserListMapper,
    private val userRouter: UserRouter,
    private val router: Router,
    private val supportRouter: SupportRouter,
    private val deleteAllEventUserCase: DeleteAllCategoryUserCase,
    private val deleteAllCategoryUseCase: DeleteAllCategoryUseCase,
) : ViewModel(), UserListMapper.ItemListProvider {

    private var loadingJob: Job? = null
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    fun onStart() {
        updateTop()
        fetchData()
    }

    private fun fetchData() {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            loadUserData()
        }
    }

    private suspend fun loadUserData() {
        runCatching {
            userDataStoreRepository.getUserId()
        }.onSuccess { userId ->
            if (userId == null) {
                userRouter.goToUser()
            } else {
                loadUsers(userId)
            }
        }.onFailure {
            updateError()
        }
    }

    private suspend fun loadUsers(
        userDataStoreId: Long
    ) {
        runCatching {
            userRepository.getUserList()
        }.onSuccess { list ->
            updateSuccess(
                userDataStoreId = userDataStoreId,
                list = list,
            )
        }.onFailure {
            updateError()
        }
    }

    private fun updateSuccess(
        userDataStoreId: Long,
        list: List<UserSelfModel>
    ) {
        _requestFlow.value = RequestItem.State.Idle
        _itemsFlow.value = userListMapper.getItems(
            userDataStoreId = userDataStoreId,
            list = list,
            provider = this
        ).ifEmpty {
            _requestFlow.value = userListMapper.getRequestEmpty()
            emptyList()
        }
        updateBottom(list.size <= 12)
    }

    private fun loadChangeUser(userId: Long) {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            runCatching {
                userDataStoreRepository.setUserId(userId)
            }.onSuccess {
                loadUserData()
            }.onFailure {
                supportRouter.showSnackBar(userListMapper.getSnackChangeError())
            }
        }
    }

    private fun loadDeleteUser(userId: Long) {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                userRepository.deleteUserById(userId)
            }.onSuccess {
                loadUserData()
            }.onFailure {
                supportRouter.showSnackBar(userListMapper.getSnackDeleteError())
                loadUserData()
            }
        }
    }

    private fun updateTop() {
        _topFlow.value = userListMapper.getToolbarItemState(
            onClickBack = ::onBackPressed,
            onClickDelete = ::onClickDeleteAll
        )
    }

    private fun onClickDeleteAll() {
        supportRouter.showAlert(userListMapper.getAlertDeleteAll(::loadDeleteAll))
    }

    private fun loadDeleteAll() {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                userRepository.deleteAll()
            }.onSuccess {
                val deleteEventAsync = async {
                    loadAllDeleteEvent()
                }

                val deleteCategoryAsync = async {
                    loadAllDeleteCategory()
                }

                listOf(
                    deleteCategoryAsync,
                    deleteEventAsync
                ).awaitAll()

                loadDeleteDataStoreUserId()
            }.onFailure {
                updateError()
            }
        }
    }

    private suspend fun loadDeleteDataStoreUserId(): Result<Unit> {
        return runCatching {
            userDataStoreRepository.setUserId(null)
        }
    }

    private suspend fun loadAllDeleteEvent(): Result<Unit> {
        return runCatching {
            deleteAllEventUserCase.invoke()
        }
    }

    private suspend fun loadAllDeleteCategory(): Result<Unit> {
        return runCatching {
            deleteAllCategoryUseCase.invoke()
        }
    }

    private fun updateBottom(isVisible: Boolean) {
        _bottomFlow.value = if (isVisible) {
            userListMapper.getButtonItemState(onClick = ::onAddNewUser)
        } else {
            null
        }
    }

    private fun updateLoading() {
        _requestFlow.value = userListMapper.getRequestLoading()
        _itemsFlow.value = emptyList()
        updateBottom(false)
    }

    private fun updateError() {
        _itemsFlow.value = emptyList()
        _requestFlow.value = userListMapper.getRequestError(::fetchData)
        updateBottom(false)
    }

    private fun onAddNewUser(state: ButtonItem.State) {
        userRouter.goToUser()
    }

    private fun onBackPressed() {
        router.pop()
    }

    override fun onClickDelete(state: UserListItem.State) {
        val data = state.data
        if (data is UserSelfModel) {
            data.userId?.let(::loadDeleteUser)
        }
    }

    override fun onClickItem(state: UserListItem.State) {
        val data = state.data
        if (data is UserSelfModel) {
            data.userId?.let(::loadChangeUser)
        }
    }

    override fun onClickEdit(state: UserListItem.State) {
        val data = state.data
        if (data is UserSelfModel) {
            userRouter.goToUser(data.userId)
        }
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}