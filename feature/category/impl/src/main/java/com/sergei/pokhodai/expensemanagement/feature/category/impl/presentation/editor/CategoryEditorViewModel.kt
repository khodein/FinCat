package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBusKeys
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryIconModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.mapper.CategoryEditorMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryEditorContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CategoryEditorViewModel(
    private val categoryRepository: CategoryRepository,
    private val categoryEditorMapper: CategoryEditorMapper,
    private val supportRouter: SupportRouter,
    private val categoryRouter: CategoryRouter,
    private val router: Router,
    private val eventBus: EventBus,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    CategoryEditorMapper.Provider {
    private var nameJob: Job? = null
    private var loadJob: Job? = null
    private val route = savedStateHandle.toRoute<CategoryEditorContract>()
    private val categoryId = route.id
    private val isOpenFromDialog = route.isOpenFromDialog
    private val budgetType = BudgetType.valueOf(route.budgetType)
    private val isEdit = categoryId != null

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _buttonFlow = MutableStateFlow<ButtonItem.State?>(null)
    val buttonFlow = _buttonFlow.asStateFlow()

    private var categoryModel: CategoryModel = CategoryModel.getDefault()
    private var previousCategoryModel: CategoryModel? = categoryModel

    init {
        eventBus.subscribe<CategoryIconModel>(
            key = EventBusKeys.CATEGORY_ICON,
            event = CategoryIconModel::class.java,
            callback = { model ->
                categoryModel = categoryModel.copy(
                    type = model.type,
                    colorName = model.colorName
                )
                updateSuccess()
            }
        )

        updateToolbar()
        loadData()
    }

    private fun loadData() {
        updateButton(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            runCatching {
                categoryId?.let {
                    categoryRepository.getCategoryById(it)
                } ?: CategoryModel.getDefault()
            }.onSuccess {
                categoryModel = it.copy(budgetType = budgetType)
                previousCategoryModel = categoryModel

                updateSuccess()
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(categoryEditorMapper.getGlobalError())
            }
        }
    }

    private fun loadSave() {
        updateButton(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                if (isEdit) {
                    categoryRepository.updateCategory(categoryModel)
                } else {
                    categoryRepository.setCategories(categoryModel)
                }
            }.onSuccess {
                updateButton()
                supportRouter.showSnackBar(categoryEditorMapper.getCategoryMessageSuccess(isEdit))
                router.pop()
                if (isOpenFromDialog) {
                    eventBus.push(categoryModel, EventBusKeys.NEW_CATEGORY_ADDED)
                }
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(categoryEditorMapper.getSaveErrorMessage())
            }
        }
    }

    private fun loadDelete() {
        updateButton(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                categoryId?.let {
                    categoryRepository.setDeleteById(it)
                } ?: throw Throwable()
            }.onSuccess {
                updateButton()
                supportRouter.showSnackBar(categoryEditorMapper.getDeleteSuccessMessage())
                router.pop()
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(categoryEditorMapper.getGlobalError())
            }
        }
    }

    private fun updateToolbar() {
        _topFlow.value = categoryEditorMapper.getToolbarState(
            isEdit = isEdit,
            onClickDelete = ::onClickDelete,
            onClickBack = ::onBackPressed
        )
    }

    private fun onClickDelete() {
        supportRouter.hideKeyboard()
        supportRouter.showAlert(
            categoryEditorMapper.getAlertQuastionDeleteModel(
                onClickCancel = ::loadDelete,
                onClickConfirm = {}
            )
        )
    }

    private fun updateButton(isLoading: Boolean = false) {
        _buttonFlow.value = categoryEditorMapper.getButtonState(
            isEdit = isEdit,
            isLoading = isLoading,
            onClick = ::onClickButton
        )
    }

    private fun updateSuccess() {
        updateToolbar()
        updateButton()
        updateItems()
    }

    private fun updateItems() {
        _itemsFlow.value = categoryEditorMapper.getItems(
            categoryModel = categoryModel,
            itemListProvider = this
        )
    }

    private fun onClickButton(state: ButtonItem.State) {
        supportRouter.hideKeyboard()
        categoryEditorMapper.getCategoryEditorErrorState(
            name = categoryModel.name,
            type = categoryModel.type
        )?.let {
            supportRouter.showSnackBar(it.message)
        } ?: run {
            loadSave()
        }
    }

    private fun onBackPressed() {
        supportRouter.hideKeyboard()
        router.pop()
    }

    override fun onClickColorPicker() {
        supportRouter.hideKeyboard()
        supportRouter.showColorPicker(
            categoryEditorMapper.getColorRouterModel(
                onClickColor = {
                    categoryModel = categoryModel.copy(colorName = "#${it}")
                    updateSuccess()
                }
            )
        )
    }

    override fun onChangeName(value: String) {
        nameJob?.cancel()
        nameJob = viewModelScope.launch {
            delay(NAME_DEBOUNCE)
            categoryModel = categoryModel.copy(name = value)
            updateSuccess()
        }
    }

    override fun onClickIcon() {
        supportRouter.hideKeyboard()
        categoryRouter.goToCategoryIcon()
    }

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(EventBusKeys.CATEGORY_ICON)
    }

    private companion object {
        const val NAME_DEBOUNCE = 300L
        const val LOADING_DEBOUNCE = 300L
    }
}