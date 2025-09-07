package com.sergei.pokhodai.expensemanagement.home.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_64
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.router.support.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
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

internal class HomeViewModel(
    private val getDateEventByMonthAndYearUseCase: GetDateEventByMonthAndYearUseCase,
    private val homeMapper: HomeMapper,
    private val supportRouter: SupportRouter,
    private val eventEditorRouter: EventEditorRouter,
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
                delay(LOADING_DEBOUNCE)
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
        val items = homeMapper.getEventStateList(
            data = data,
            onClickEvent = ::onClickEvent
        )

        _itemsFlow.value = items.apply {
            _requestFlow.value = RequestItem.State.Idle
        }.ifEmpty {
            _requestFlow.value = RequestItem.State.Empty(
                message = homeMapper.getEmptyText()
            )
            emptyList()
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
        focusDate = focusDate.minusMonths(1)
        updateTop()
        fetchData(true)
    }

    private fun onClickMinusFocusDate() {
        focusDate = focusDate.plusMonths(1)
        updateTop()
        fetchData(true)
    }

    private fun onClickCalendar() {
        supportRouter.showCalendar(
            CalendarRouterModel(
                value = focusDate,
                start = LocalDateFormatter.today().minusYears(2),
                end = LocalDateFormatter.today().plusYears(2),
                onClick = {
                    focusDate = it
                    updateTop()
                    fetchData(true)
                }
            )
        )
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}