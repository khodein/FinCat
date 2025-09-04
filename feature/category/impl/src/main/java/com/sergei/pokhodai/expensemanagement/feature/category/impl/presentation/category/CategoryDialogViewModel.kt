package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBusKeys
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryDialogMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CategoryDialogViewModel(
    private val categoryRepository: CategoryRepository,
    private val categoryDialogMapper: CategoryDialogMapper,
    private val eventBus: EventBus,
    private val router: Router,
    private val categoryRouter: CategoryRouter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<CategoryDialogContract>()
    private val budgetType = BudgetType.valueOf(route.budgetType)
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<String?>(null)
    val topFlow = _topFlow.asStateFlow()

    init {
        loadData()
        updateBottom()
        updateTop()
    }

    private fun updateTop() {
        _topFlow.value = categoryDialogMapper.getTitle()
    }

    private fun loadData() {
        viewModelScope.launch {
            runCatching {
                categoryRepository.getCategoryListByBudgetType(budgetType.name)
            }.onSuccess(::updateItems)
        }
    }

    private fun updateItems(list: List<CategoryModel>) {
        _itemsFlow.value = categoryDialogMapper.getItems(
            budgetType = budgetType,
            list = list,
            onClick = ::onClickItem
        )
    }

    private fun updateBottom() {
        _bottomFlow.value = categoryDialogMapper.getButtonItemState(::onClickButton)
    }

    private fun onClickItem(state: CategoryKindItem.State) {
        val data = state.data
        if (data is CategoryModel) {
            eventBus.push(data, EventBusKeys.CATEGORY)
            router.pop()
        }
    }

    private fun onClickButton(state: ButtonItem.State) {
        router.pop()
        categoryRouter.goToCategoryEditor(isOpenFromDialog = true, budgetType = budgetType.name)
    }
}