package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.CategoryModule
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.mapper.CategoryManagerMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager.CategoryManagerItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CategoryManagerViewModel(
    private val supportRouter: SupportRouter,
    private val categoryRouter: CategoryRouter,
    private val categoryRepository: CategoryRepository,
    private val router: Router,
    private val eventBus: EventBus,
    private val categoryManagerMapper: CategoryManagerMapper,
) : ViewModel() {

    private var loadingJob: Job? = null
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    init {
        eventBus.subscribe(
            key = CategoryModule.Keys.NEW_CATEGORY_CREATED_OR_EDIT,
            event = CategoryModel::class.java,
            callback = {
                onClickReload()
            }
        )
        updateTop(false)
        fetchData()
    }

    private fun fetchData() {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            loadAllList()
        }
    }

    private suspend fun loadAllList() {
        runCatching {
            categoryRepository.getAllCategories()
        }.onSuccess { list ->
            updateSuccess(list = list)
        }.onFailure {
            updateError()
        }
    }

    private suspend fun loadDeleteAll() {
        runCatching {
            categoryRepository.onDeleteAll()
        }.onSuccess {
            supportRouter.showSnackBar(categoryManagerMapper.getDeleteAllSuccessMessage())
            loadAllList()
        }.onFailure {
            updateError()
        }
    }

    private suspend fun loadDeleteById(categoryId: Long) {
        runCatching {
            categoryRepository.setDeleteById(categoryId)
        }.onSuccess {
            supportRouter.showSnackBar(categoryManagerMapper.getDeleteCategoryIdSuccessMessage())
            loadAllList()
        }.onFailure {
            supportRouter.showSnackBar(categoryManagerMapper.getDeleteCategoryIdErrorMessage())
        }
    }

    private fun updateSuccess(
        list: List<CategoryModel>
    ) {
        updateBottom(true)
        updateTop(true)
        _requestFlow.value = RequestItem.State.Idle
        _itemsFlow.value = categoryManagerMapper.getItems(
            list = list,
            onClickEdit = ::onClickEdit,
            onClickDelete = ::onClickDelete
        ).ifEmpty {
            _requestFlow.value = categoryManagerMapper.getRequestEmptyState()
            updateTop(false)
            emptyList()
        }
    }

    private fun onClickEdit(state: CategoryManagerItem.State) {
        val data = state.data
        if (data is CategoryModel) {
            categoryRouter.goToCategoryEditor(
                id = data.id,
                budgetType = data.budgetType?.name.orEmpty()
            )
        }
    }

    private fun onClickDelete(state: CategoryManagerItem.State) {
        val data = state.data
        if (data is CategoryModel) {
            val categoryId = data.id
            if (categoryId != null) {
                supportRouter.showAlert(
                    categoryManagerMapper.getAlertDeleteCategoryId(
                        onClickConfirm = {
                            loadingJob?.cancel()
                            loadingJob = viewModelScope.launch {
                                loadDeleteById(categoryId)
                            }
                        }
                    )
                )
            }
        }
    }

    private fun updateLoading() {
        _requestFlow.value = RequestItem.State.Loading
        _itemsFlow.value = emptyList()
        updateBottom(false)
    }

    private fun updateError() {
        _itemsFlow.value = emptyList()
        _requestFlow.value = categoryManagerMapper.getRequestErrorState(::onClickReload)
        updateBottom(false)

    }

    private fun updateTop(isDelete: Boolean) {
        _topFlow.value = categoryManagerMapper.getToolbarItemState(
            isDelete = isDelete,
            onClickBack = ::onBackPressed,
            onClickDelete = ::onClickDeleteAll
        )
    }

    private fun updateBottom(isVisible: Boolean) {
        _bottomFlow.value = if (isVisible) {
            categoryManagerMapper.getButtonItemState(::onClickNewCategory)
        } else {
            null
        }
    }

    private fun onClickDeleteAll() {
        supportRouter.showAlert(
            categoryManagerMapper.getAlertDeleteAll(
                onClickConfirm = {
                    updateLoading()
                    loadingJob?.cancel()
                    loadingJob = viewModelScope.launch {
                        delay(LOADING_DEBOUNCE)
                        loadDeleteAll()
                    }
                }
            )
        )
    }

    private fun onClickReload() {
        fetchData()
    }

    private fun onClickNewCategory(state: ButtonItem.State) {
        categoryRouter.goToCategoryEditor(id = null, budgetType = null)
    }

    private fun onBackPressed() {
        router.pop()
    }

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(CategoryModule.Keys.NEW_CATEGORY_CREATED_OR_EDIT)
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}