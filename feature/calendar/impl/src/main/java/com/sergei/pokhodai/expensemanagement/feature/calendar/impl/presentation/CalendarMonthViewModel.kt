package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.calendar.CalendarMonthKeys
import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.mapper.CalendarMonthMapper
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.contract.CalendarMonthContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CalendarMonthViewModel(
    private val calendarMonthMapper: CalendarMonthMapper,
    private val eventBus: EventBus,
    private val router: Router,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<CalendarMonthContract>()
    private var month = CalendarMonthModel.valueOf(route.month)
    private val previousMonth = month
    private val isHome = route.isHome
    private val isReport = route.isReport
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    init {
        updateBottom()
        loadData()
    }

    private fun updateBottom() {
        _bottomFlow.value = calendarMonthMapper.getButtonState(::onClickButton)
    }

    private fun loadData() {
        _itemsFlow.value = calendarMonthMapper.getItems(
            month = month,
            list = CalendarMonthModel.entries,
            onClickTag = ::onClickTag
        )
    }

    private fun onClickButton(state: ButtonItem.State) {
        if (month != previousMonth) {
            when {
                isReport -> CalendarMonthKeys.CHANGE_CALENDAR_MONTH_REPORT
                isHome -> CalendarMonthKeys.CHANGE_CALENDAR_MONTH_HOME
                else -> null
            }?.let { key ->
                eventBus.push(month, key)
            }
        }
        router.pop()
    }

    private fun onClickTag(state: TagItem.State) {
        val data = state.data
        if (data is CalendarMonthModel) {
            month = data
            loadData()
        }
    }
}