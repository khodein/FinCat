package com.sergei.pokhodai.expensemanagement.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.repositories.WalletRepository
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.ui.home.HomeFragment
import com.sergei.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.utils.ManagerUtils
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.enums.Wallets
import com.sergei.pokhodai.expensemanagement.utils.insertEmptyItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    managerUtils: ManagerUtils,
    val userDataSource: UserDataSource,
    languageRepository: LanguageRepository
) : ViewModel() {

    val currency = userDataSource.currency ?: Currency.DOLLAR

    private val language = languageRepository.getLanguage()
    private val _dateFlow = MutableStateFlow(LocalDateFormatter.now())
    val dateFlow = _dateFlow.asStateFlow()

    private val income = managerUtils.getString(Wallets.INCOME.resId)
    private val expense = managerUtils.getString(Wallets.EXPENSE.resId)

    private val _refreshFlow =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private val _statusFlow = MutableStateFlow(Triple(0, 0, 0))
    val statusFlow = _statusFlow.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val walletsFlow = combine(
        _dateFlow,
        _refreshFlow
    ) { date, _ ->
        walletRepository.getPaginationWalletsByDate(date.MMMM_yyyy(language = language))
    }.flatMapLatest { pager ->
        pager.flow.map { pagerData ->
            pagerData.map<WalletEntity, WalletAdapter.ItemWallet> { wallet ->
                WalletAdapter.ItemWallet.WrapWallet(wallet = wallet)
            }.insertEmptyItem(WalletAdapter.ItemWallet.EmptyWallet)
                .insertSeparators { before, after ->
                    when {
                        before == null && after is WalletAdapter.ItemWallet.WrapWallet -> {
                            val count = walletRepository.sumByPublicatedAt(after.wallet.dateFormat)
                            WalletAdapter.ItemWallet.WrapHeader(
                                after.wallet.publicatedAt,
                                count,
                                after.wallet.currency
                            )
                        }
                        before is WalletAdapter.ItemWallet.WrapWallet && after is WalletAdapter.ItemWallet.WrapWallet -> {
                            if (before.wallet.publicatedAt.dd_MMMM_yyyy(language) != after.wallet.publicatedAt.dd_MMMM_yyyy(language)) {
                                val count =
                                    walletRepository.sumByPublicatedAt(after.wallet.dateFormat)
                                before.bottom = true
                                WalletAdapter.ItemWallet.WrapHeader(
                                    after.wallet.publicatedAt,
                                    count,
                                    after.wallet.currency
                                )
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

        onSumIncomeAndExpense()
    }

    private fun onSumIncomeAndExpense(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            val income: Deferred<Int?> = async {
                walletRepository.sumByType(
                    type = income,
                    date = dateFlow.value.MMMM_yyyy(language)
                )
            }
            val expense: Deferred<Int?> = async {
                walletRepository.sumByType(
                    type = expense,
                    date = dateFlow.value.MMMM_yyyy(language)
                )
            }
            onChangeBalance(income.await(), expense.await())
        }
    }

    private fun onChangeBalance(income: Int?, expense: Int?) {
        _statusFlow.value = Triple(first = income ?: 0, third = expense ?: 0, second = income?.plus(expense ?: 0) ?: 0)
    }

    fun onChangeMonthDate(date: LocalDateFormatter) {
        _dateFlow.update { date }

        onSumIncomeAndExpense()
    }

    fun onClickDeleteWallet(
        id: Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            walletRepository.deleteWalletById(id)
            onSwipeRefresh()
        }
    }
}