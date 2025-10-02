package com.sergei.pokhodai.expensemanagement.home.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.calendar.CalendarMonthKeys
import com.sergei.pokhodai.expensemanagement.feature.calendar.api.CalendarMonthRouter
import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetDateEventByMonthAndYearUseCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.router.EventEditorRouter
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.mapper.HomeMapper
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.calendar.CalendarItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.number

internal class HomeViewModel(
    private val getDateEventByMonthAndYearUseCase: GetDateEventByMonthAndYearUseCase,
    private val homeMapper: HomeMapper,
    private val eventEditorRouter: EventEditorRouter,
    private val calendarMonthRouter: CalendarMonthRouter,
    private val eventBus: EventBus,
) : ViewModel() {

    private var fetchJob: Job? = null
    private val _topFlow = MutableStateFlow<CalendarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    private var focusDate = LocalDateFormatter.today()

    private var isFirstLoading = true

    init {
        eventBus.subscribe(
            key = CalendarMonthKeys.CHANGE_CALENDAR_MONTH_HOME,
            event = CalendarMonthModel::class.java,
            callback = ::onChangeMonth
        )
    }

    private fun onChangeMonth(model: CalendarMonthModel) {
        val index = CalendarMonthModel.entries.indexOf(model) + 1
        focusDate = focusDate.changeMonth(index)
        updateTop()
        fetchData()
    }

    fun onStart() {
        updateBottom()
        updateTop()
        fetchData(isFirstLoading)
        if (isFirstLoading) {
            isFirstLoading = false
        }
    }

    private fun updateTop() {
        _topFlow.value = CalendarItem.State(
            provideId = "home_event_calendar_id",
            text = homeMapper.getCalendarText(focusDate),
            onClickCalendar = ::onClickCalendar,
            onClickLeft = ::onClickMinusFocusDate,
            onClickRight = ::onClickPlusFocusDate
        )
    }

    private fun fetchData(isLoading: Boolean = true) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (isLoading) {
                updateLoading()
            }
            runCatching {
                getDateEventByMonthAndYearUseCase.invoke(date = focusDate)
            }
                .onSuccess(::updateSuccess)
                .onFailure(::updateError)
        }
    }

    private fun updateLoading() {
        _itemsFlow.value = emptyList()
        _requestFlow.value = RequestItem.State.Loading
    }

    private fun updateSuccess(data: Map<DateModel, List<EventModel>>) {
        _requestFlow.value = RequestItem.State.Idle

        _itemsFlow.value = homeMapper.getEventStateList(
            data = data,
            onClickEvent = ::onClickEvent
        ).ifEmpty {
            homeMapper.getEmptyItems()
        }
    }

    private fun updateError(throwable: Throwable) {
        _requestFlow.value = RequestItem.State.Error(
            message = homeMapper.getErrorText(),
            onClickReload = ::fetchData
        )
    }

    private fun onClickEvent(state: EventItem.State) {
        val data = state.data
        if (data is EventModel) {
            eventEditorRouter.goToEventEditor(data.id)
        }
    }

    private fun updateBottom() {
        _bottomFlow.value = homeMapper.getBottomButtonState(::onClickBottom)
    }

    private fun onClickBottom(state: ButtonItem.State) {
        eventEditorRouter.goToEventEditor(null)
    }

    private fun onClickPlusFocusDate() {
        focusDate = focusDate.plusMonths(1)
        updateTop()
        fetchData(true)
    }

    private fun onClickMinusFocusDate() {
        focusDate = focusDate.minusMonths(1)
        updateTop()
        fetchData(true)
    }

    private fun onClickCalendar() {
        CalendarMonthModel.entries.getOrNull(focusDate.month.number - 1)?.let {
            calendarMonthRouter.goToCalendarMonth(month = it, isHome = true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventBus.unsubscribe(CalendarMonthKeys.CHANGE_CALENDAR_MONTH_HOME)
    }
}