package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.domain.model.CbrDailyModel
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data.ExchangeRateRepository
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.mapper.ExchangeRateMapper
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ExchangeRateViewModel(
    private val router: Router,
    private val exchangeRateMapper: ExchangeRateMapper,
    private val exchangeRateRepository: ExchangeRateRepository,
) : ViewModel() {
    private var loadingJob: Job? = null

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _requestFlow = MutableStateFlow<RequestItem.State>(RequestItem.State.Idle)
    val requestFlow = _requestFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    init {
        updateTop()
        fetchData()
    }

    private fun fetchData() {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            loadData(false)
        }
    }

    private fun refreshData() {
        updateLoading()
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            delay(DEBOUNCE_REFRESH)
            loadData(true)
        }
    }

    private suspend fun loadData(isUpdateCache: Boolean) {
        runCatching {
            exchangeRateRepository.getDailyValuteList(isUpdateCache)
        }
            .onSuccess { list ->
                if (list.isEmpty()) {
                    updateEmpty()
                } else {
                    updateSuccess(list)
                }
            }
            .onFailure(::updateError)
    }

    private fun updateLoading() {
        _itemsFlow.value = emptyList()
        _requestFlow.value = RequestItem.State.Loading
    }

    private fun updateSuccess(list: List<CbrDailyModel.ValuteModel>) {
        _requestFlow.value = RequestItem.State.Idle
        _itemsFlow.value = exchangeRateMapper.getItems(list)
    }

    private fun updateEmpty() {
        _requestFlow.value = exchangeRateMapper.getRequestEmptyState()
        _itemsFlow.value = emptyList()
    }

    private fun updateError(error: Throwable) {
        _requestFlow.value = exchangeRateMapper.getRequestErrorState(::refreshData)
        _itemsFlow.value = emptyList()
    }

    private fun updateTop() {
        _topFlow.value = exchangeRateMapper.getToolbarItemState(::onBackPressed)
    }

    private fun onBackPressed() {
        router.pop()
    }

    private companion object {
        const val DEBOUNCE_REFRESH = 300L
    }
}