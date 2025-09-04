package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_64
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.router.support.calendar.CalendarRouterModel
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.feature.report.impl.data.report.ReportRepository
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetCategoryEventByMonthAndYearAndBudgetTypeUceCase
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.mapper.ReportMapper
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.calendar.CalendarItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

internal class ReportViewModel(
    private val reportRepository: ReportRepository,
    private val getCategoryEventByMonthAndYearAndBudgetTypeUceCase: GetCategoryEventByMonthAndYearAndBudgetTypeUceCase,
    private val reportMapper: ReportMapper,
    private val supportRouter: SupportRouter,
) : ViewModel() {

    private var fetchJob: Job? = null
    private var loadingJob: Job? = null
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<CalendarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _shareFlow = MutableStateFlow<File?>(null)
    val shareFlow = _shareFlow.asStateFlow()

    private var focusDate = LocalDateFormatter.today()
    private var expenseData: Map<CategoryModel, List<EventModel>> = emptyMap()
    private var incomeData: Map<CategoryModel, List<EventModel>> = emptyMap()

    private var isFirstLoading = true

    fun onStart() {
        updateBottom()
        updateTop()
        fetchData(isFirstLoading)
        if (isFirstLoading) {
            isFirstLoading = false
        }
    }

    private fun fetchData(isLoading: Boolean = true) {
        fetchJob?.cancel()
        updateBottom(isLoading)
        fetchJob = viewModelScope.launch {
            if (isLoading) {
                updateLoading()
                delay(LOADING_DEBOUNCE)
            }

            runCatching {
                val expenseData = getCategoryEventByMonthAndYearAndBudgetTypeUceCase.invoke(
                    date = focusDate,
                    budgetType = BudgetType.EXPENSE
                )

                async {  }
                val incomeData = getCategoryEventByMonthAndYearAndBudgetTypeUceCase.invoke(
                    date = focusDate,
                    budgetType = BudgetType.INCOME
                )

                expenseData to incomeData
            }
                .onSuccess(::updateSuccess)
                .onFailure(::updateError)
        }
    }

    private fun updateBottom(isLoading: Boolean = false) {
        _bottomFlow.value = if (expenseData.isEmpty() && incomeData.isEmpty()) {
            null
        } else {
            reportMapper.getBottomButtonState(
                isLoading = isLoading,
                onClick = ::onClickDownload
            )
        }
    }

    private fun updateTop() {
        _topFlow.value = CalendarItem.State(
            provideId = "report_calendar_id",
            text = reportMapper.getCalendarText(focusDate),
            onClickLeft = ::onClickLeftCalendar,
            onClickRight = ::onClickRightCalendar,
            onClickCalendar = ::onClickCalendar
        )
    }

    private fun updateSuccess(
        data: Pair<Map<CategoryModel, List<EventModel>>, Map<CategoryModel, List<EventModel>>>
    ) {
        this.expenseData = data.first
        this.incomeData = data.second

        buildList {
            reportMapper.getItems(isExpense = true, data = expenseData).let(::addAll)
            reportMapper.getItems(isExpense = false, data = incomeData).let(::addAll)
        }.let {
            _itemsFlow.value = it.ifEmpty {
                _requestFlow.value = RequestItem.State.Empty(
                    message = reportMapper.getEmptyText(),
                    container = RequestItem.Container(paddings = P_0_0_0_64)
                )
                emptyList()
            }
        }

        _requestFlow.value = RequestItem.State.Idle
        updateBottom()
    }

    private fun updateLoading() {
        _itemsFlow.value = emptyList()
        _requestFlow.value = RequestItem.State.Loading
    }

    private fun updateError(throwable: Throwable) {
        updateBottom()
        _itemsFlow.value = emptyList()
        _requestFlow.value = RequestItem.State.Error(
            message = reportMapper.getErrorText(),
            container = RequestItem.Container(paddings = P_0_0_0_64),
            onClickReload = ::fetchData
        )
    }

    private fun onClickDownload(state: ButtonItem.State) {
        downloadExcel()
    }

    private fun downloadExcel() {
        updateBottom(isLoading = true)
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(LOADING_DEBOUNCE)
            runCatching {
                val fileName = "fincat_report_${focusDate.MM_yyyy()}"
                val expenseEvents = expenseData.values.flatten()
                val incomeEvents = incomeData.values.flatten()
                val expenseReport = reportMapper.getReports(true, expenseEvents)
                val incomeReport = reportMapper.getReports(false, incomeEvents)
                reportRepository.getReportsToExcelFile(
                    expenseReport = expenseReport,
                    incomeReport = incomeReport,
                    fileName = fileName
                )
            }.onSuccess { file ->
                _shareFlow.value = file
                _shareFlow.value = null
                updateBottom()
            }.onFailure {
                supportRouter.showSnackBar(reportMapper.getErrorText())
                updateBottom()
            }
        }
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

    private fun onClickLeftCalendar() {
        focusDate = focusDate.minusMonths(1)
        updateTop()
        fetchData(true)
    }

    private fun onClickRightCalendar() {
        focusDate = focusDate.plusMonths(1)
        updateTop()
        fetchData(true)
    }

    private companion object {
        const val LOADING_DEBOUNCE = 300L
    }
}