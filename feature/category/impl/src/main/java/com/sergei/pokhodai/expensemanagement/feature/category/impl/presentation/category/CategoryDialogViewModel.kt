package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.mapper.CategoryDialogMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.EventKeys
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.DeleteAllCategoryUserCase
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CategoryDialogViewModel(
    private val categoryRepository: CategoryRepository,
    private val categoryDialogMapper: CategoryDialogMapper,
    private val getCategoryDefaultListUserCase: GetCategoryDefaultListUseCase,
    private val setCategoriesUseCase: SetCategoriesUseCase,
    private val eventBus: EventBus,
    private val router: Router,
    private val categoryRouter: CategoryRouter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var loadingJob: Job? = null
    private val route = savedStateHandle.toRoute<CategoryDialogContract>()
    private val budgetType = BudgetType.valueOf(route.budgetType)
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<String?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    fun onStart() {
        updateLoading()
        updateTop()
        fetchData()
    }

    private fun updateTop() {
        _topFlow.value = categoryDialogMapper.getTitle()
    }

    private fun fetchData() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        runCatching {
            categoryRepository.getCategoryListByBudgetType(budgetType.name)
        }
            .onSuccess(::updateSuccess)
            .onFailure { updateError() }
    }

    private fun updateSuccess(list: List<CategoryModel>) {
        updateBottom(true)
        _requestFlow.value = RequestItem.State.Idle
        _itemsFlow.value = categoryDialogMapper.getItems(
            budgetType = budgetType,
            list = list,
            onClick = ::onClickItem
        ).ifEmpty {
            _requestFlow.value = categoryDialogMapper.getRequestEmpty(::onClickEmpty)
            emptyList()
        }
    }

    private fun onClickEmpty() {
        updateLoading()
        loadCreateDefault()
    }

    private fun loadCreateDefault() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                setCategoriesUseCase.invoke(*getCategoryDefaultListUserCase.invoke().toTypedArray())
            }.onSuccess {
                loadData()
            }.onFailure {
                updateError()
            }
        }
    }

    private fun updateBottom(isVisible: Boolean) {
        _bottomFlow.value = if (isVisible) {
            categoryDialogMapper.getButtonItemState(::onClickButton)
        } else {
            null
        }
    }

    private fun updateLoading() {
        _requestFlow.value = RequestItem.State.Loading
        _itemsFlow.value = emptyList()
        updateBottom(false)
    }

    private fun updateError() {
        _requestFlow.value = categoryDialogMapper.getRequestError(::fetchData)
        _itemsFlow.value = emptyList()
        updateBottom(false)
    }

    private fun onClickItem(state: CategoryKindItem.State) {
        val data = state.data
        if (data is CategoryModel) {
            eventBus.push(data, EventKeys.NEW_CATEGORY_ADDED)
            router.pop()
        }
    }

    private fun onClickButton(state: ButtonItem.State) {
        router.pop()
        categoryRouter.goToCategoryEditor(isOpenFromDialog = true, budgetType = budgetType.name)
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}