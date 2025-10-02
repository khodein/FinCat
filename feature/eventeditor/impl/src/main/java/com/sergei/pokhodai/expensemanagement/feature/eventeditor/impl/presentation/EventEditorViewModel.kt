package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.support.api.model.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.router.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.EventKeys
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.AmountModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.EventRepository
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.mapper.EventEditorMapper
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.contract.EventEditorRouterContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.dropdown.DropDownItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

internal class EventEditorViewModel(
    private val eventEditorMapper: EventEditorMapper,
    private val eventRepository: EventRepository,
    private val router: Router,
    private val supportRouter: SupportRouter,
    private val categoryRouter: CategoryRouter,
    private val eventBus: EventBus,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    EventEditorMapper.Provider {
    private var editableJob: Job? = null
    private var loadJob: Job? = null
    private val eventEditorRoute = savedStateHandle.toRoute<EventEditorRouterContract>()
    private val editorId = eventEditorRoute.eventId
    private val isEdit: Boolean = editorId != null
    private var eventModel = EventModel.getDefault()

    private val _topBarFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val toolbarFlow = _topBarFlow.asStateFlow()

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemFlow = _itemsFlow.asStateFlow()

    private val _buttonFlow = MutableStateFlow<ButtonItem.State?>(null)
    val buttonFlow = _buttonFlow.asStateFlow()

    init {
        eventBus.subscribe<CategoryModel>(
            key = EventKeys.SHOW_CATEGORY,
            event = CategoryModel::class.java,
            callback = { model ->
                onClickCategory()
            }
        )

        eventBus.subscribe<CategoryModel>(
            key = EventKeys.NEW_CATEGORY_ADDED,
            event = CategoryModel::class.java,
            callback = { model ->
                eventModel = eventModel.copy(categoryModel = model)
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
                editorId?.let {
                    eventRepository.getEventById(it)
                } ?: EventModel.getDefault()
            }.onSuccess { model ->
                eventModel = model
                updateButton()
                updateSuccess()
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(eventEditorMapper.getGlobalError())
            }
        }
    }

    private fun loadSave() {
        updateButton(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            runCatching {
                if (isEdit) {
                    eventRepository.updateEvent(eventModel)
                } else {
                    eventRepository.setEvents(eventModel)
                }
            }.onSuccess {
                updateButton()
                supportRouter.showSnackBar(eventEditorMapper.getSaveMessageSuccess(isEdit))
                router.pop()
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(eventEditorMapper.getSaveErrorMessage())
            }
        }
    }

    private fun loadDelete() {
        updateButton(isLoading = true)
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            runCatching {
                editorId?.let {
                    eventRepository.setDeleteEventById(it)
                } ?: throw Throwable()
            }.onSuccess {
                updateButton()
                supportRouter.showSnackBar(eventEditorMapper.getDeleteMessageSuccess())
                router.pop()
            }.onFailure {
                updateButton()
                supportRouter.showSnackBar(eventEditorMapper.getGlobalError())
            }
        }
    }

    private fun updateSuccess() {
        updateButton()
        updateItems()
    }

    private fun updateItems() {
        _itemsFlow.value = eventEditorMapper.getItems(
            eventModel = eventModel,
            itemListProvider = this
        )
    }

    override fun onClickDate() {
        supportRouter.hideKeyboard()
        supportRouter.showCalendar(
            CalendarRouterModel(
                value = eventModel.dateModel.value,
                start = LocalDateFormatter.today().minusYears(2),
                end = LocalDateFormatter.today().plusYears(2),
                onClick = {
                    eventModel = eventModel.copy(
                        dateModel = DateModel(
                            value = it.endOfTheDay()
                        )
                    )
                    updateSuccess()
                }
            )
        )
    }

    override fun onClickCategory() {
        categoryRouter.goToCategoryDialog(eventModel.budgetType.name)
    }

    override fun onChangeAmount(value: String, money: BigDecimal) {
        onChangeDebounce {
            eventModel = eventModel.copy(
                amountModel = if (money.compareTo(BigDecimal.ZERO) == 0) {
                    AmountModel.getDefault()
                } else {
                    AmountModel(
                        value = money,
                        format = value
                    )
                }
            )
            updateSuccess()
        }
    }

    override fun onChangeDescription(value: String) {
        onChangeDebounce {
            eventModel = eventModel.copy(description = value)
            updateSuccess()
        }
    }

    private fun updateToolbar() {
        _topBarFlow.value = eventEditorMapper.getToolbarState(
            isEdit = isEdit,
            onClickBack = ::onClickBack,
            onClickDelete = ::onClickDelete
        )
    }

    private fun onClickDelete() {
        supportRouter.showAlert(
            eventEditorMapper.getDeleteQuestionAlert(
                onClickConfirm = ::loadDelete,
                onClickCancel = {}
            )
        )
    }

    private fun updateButton(isLoading: Boolean = false) {
        _buttonFlow.value = eventEditorMapper.getButtonState(
            isEdit = isEdit,
            isLoading = isLoading,
            budgetType = eventModel.budgetType,
            onClick = ::onClickEdit
        )
    }

    override fun onClickBudgetType(item: DropDownItem.Item) {
        supportRouter.hideKeyboard()
        val data = item.data
        if (data is BudgetType) {
            eventModel = eventModel.copy(budgetType = data)
            updateSuccess()
        }
    }

    private fun onClickBack() {
        router.pop()
    }

    private fun onClickEdit(state: ButtonItem.State) {
        supportRouter.hideKeyboard()
        eventEditorMapper.getErrorState(
            categoryModel = eventModel.categoryModel,
            amountModel = eventModel.amountModel
        )?.let {
            supportRouter.showSnackBar(it.message)
        } ?: run {
            loadSave()
        }
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

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(EventKeys.SHOW_CATEGORY)
        eventBus.unsubscribe(EventKeys.NEW_CATEGORY_ADDED)
    }

    private companion object {
        const val EDITABLE_DEBOUNCE = 200L
    }
}