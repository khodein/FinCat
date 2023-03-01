package com.android.pokhodai.expensemanagement.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
) : ViewModel() {

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.now())
    val dateFlow = _dateFlow.asStateFlow()

    private val _incomeFlow = MutableStateFlow(0)
    val incomeFlow = _incomeFlow.asStateFlow()

    private val _expenseFlow = MutableStateFlow(0)
    val expenseFlow = _expenseFlow.asStateFlow()

    val balanceFlow = combine(
        _expenseFlow,
        _incomeFlow
    ) { expense, income ->
        income - expense
    }

    private val _refreshFlow =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    init {
        onChangeIncome()
        onChangeExpense()
        onSwipeRefresh()
//        var date = LocalDateFormatter.today()
//            .update { minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()) }
//        viewModelScope.launch {
//            repeat(200) {
//                walletRepository.insertAllWallet(
//                    WalletEntity(
//                        categoryName = "100",
//                        icons = Icons.ALLOWANCE,
//                        amount = "100",
//                        type = "Income",
//                        publicatedAt = date,
//                        monthAndYear = date.MMMM_yyyy(),
//                    )
//                )
//
//                date = date.update { plusDays(1) }
//            }
//        }

//        Log.d("TAGATAG ", "tag "+walletRepository.getAllWallets())
    }

    private val _walletsFlow = MutableStateFlow<List<WalletEntity>>(emptyList())
    val walletsFlow = _walletsFlow.map {
        it.getItemsWallet().ifEmpty {
            listOf(WalletAdapter.ItemWallet.EmptyWallet)
        }
    }

    val wallets = combine(
        _dateFlow,
        _refreshFlow
    ) { dateFlow, _ ->
        dateFlow
    }.map { date ->
        onChangeWalletByMonth(date = date)
    }

    private fun onChangeWalletByMonth(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        date: LocalDateFormatter
    ) {
        viewModelScope.launch(dispatcher) {
            _walletsFlow.value = walletRepository.getWalletByMonth(date.MMMM_yyyy())
        }
    }

    private fun List<WalletEntity>.getItemsWallet(
    ): List<WalletAdapter.ItemWallet> {
        val items = mutableListOf<WalletAdapter.ItemWallet>()
        this.sortedBy { it.publicatedAt }
            .groupBy { it.publicatedAt }
            .forEach {
                items += WalletAdapter.ItemWallet.WrapHeader(date = it.key, count = "")
                items += it.value.map { wallet ->
                    WalletAdapter.ItemWallet.WrapWallet(wallet)
                }.reversed()
            }
        return items
    }

    fun onSwipeRefresh() {
        _refreshFlow.tryEmit(Unit)
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

        onChangeIncome()
        onChangeExpense()
    }

    private fun onChangeIncome(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _incomeFlow.value =
                walletRepository.sumByType(type = "Income", date = dateFlow.value.MMMM_yyyy())
        }
    }

    private fun onChangeExpense(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _expenseFlow.value =
                walletRepository.sumByType(type = "Expense", date = dateFlow.value.MMMM_yyyy())
        }
    }

    fun onChangeMonthDate(date: LocalDateFormatter) {
        _dateFlow.update { date }
    }

    fun onClickDeleteWallet(
        id: Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            walletRepository.deleteWalletById(id)
            _walletsFlow.update { list ->
                list.toMutableList().apply {
                    val wallet = find { wallets -> wallets.id == id }
                    remove(wallet)
                }
            }
        }
    }
}