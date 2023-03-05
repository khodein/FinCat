package com.android.pokhodai.expensemanagement.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.data.service.WalletDao
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.android.pokhodai.expensemanagement.ui.home.source.WalletPagingSource
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.insertEmptyItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    private val walletDao: WalletDao,
) : ViewModel() {

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.now())
    val dateFlow = _dateFlow.asStateFlow()

    private val _refreshFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    private val _statusFlow = MutableStateFlow<Triple<Int, Int, Int>>(Triple(0, 0, 0))
    val statusFlow = _statusFlow.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val walletsFlow = combine(
        _dateFlow,
        _refreshFlow
    ) { date, _ ->
        setPager(date.MMMM_yyyy())
    }.flatMapLatest { pager ->
        pager.flow.map { pagerData ->
            pagerData.map<WalletEntity, WalletAdapter.ItemWallet> { wallet ->
                WalletAdapter.ItemWallet.WrapWallet(wallet = wallet)
            }.insertEmptyItem(WalletAdapter.ItemWallet.EmptyWallet)
                .insertSeparators { before, after ->
                    when {
                        before == null && after is WalletAdapter.ItemWallet.WrapWallet -> {
                            val count = walletRepository.sumByPublicatedAt(after.wallet.dateFormat)
                            WalletAdapter.ItemWallet.WrapHeader(after.wallet.publicatedAt, count.toString())
                        }
                        before is WalletAdapter.ItemWallet.WrapWallet && after is WalletAdapter.ItemWallet.WrapWallet -> {
                            if (before.wallet.publicatedAt.dd_MM_yyyy() != after.wallet.publicatedAt.dd_MM_yyyy()) {
                                before.bottom = true
                                val count = walletRepository.sumByPublicatedAt(after.wallet.dateFormat)
                                WalletAdapter.ItemWallet.WrapHeader(after.wallet.publicatedAt, count.toString())
                            } else {
                                null
                            }
                        }
                        before is WalletAdapter.ItemWallet.WrapWallet && after == null -> {
                            before.bottom = true
                            null
                        }
                        else -> null
                    }
                }
        }.cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    private fun setPager(date: String) = Pager(PagingConfig(10)) {
        WalletPagingSource(date = date, walletDao = walletDao)
    }

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh() {
        _refreshFlow.tryEmit(Unit)
        onSumIncomeAndExpense()
    }

    fun onChangePreviousOrNextMonth(
        const: String
    ) {
        _dateFlow.update {
            it.update {
                if (const == HomeFragment.PLUS)
                    plusMonths(1)
                else minusMonths(1)
            }
        }
    }

    private fun onSumIncomeAndExpense(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            val income = async { walletRepository.sumByType(type = "Income", date = dateFlow.value.MMMM_yyyy()) }
            val expense = async { walletRepository.sumByType(type = "Expense", date = dateFlow.value.MMMM_yyyy()) }
            onChangeBalance(income.await(), expense.await())
        }
    }

    private fun onChangeBalance(income: Int, expense: Int) {
        _statusFlow.value = Triple(first = income, second = income - expense, third = expense)
    }

    fun onChangeMonthDate(date: LocalDateFormatter) {
        _dateFlow.update { date }
    }

    fun onClickDeleteWallet(
        id: Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    }
}