package com.android.pokhodai.expensemanagement.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.modals.ReportWallet
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.ui.home.HomeFragment
import com.android.pokhodai.expensemanagement.ui.report.adapter.ReportAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.android.pokhodai.expensemanagement.utils.enums.Wallets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: WalletRepository,
    managerUtils: ManagerUtils
) : ViewModel() {

    private val today = LocalDateFormatter.today()

    private val _dateFlow = MutableStateFlow(today)
    val dateFlow = _dateFlow.asStateFlow()

    private val income = managerUtils.getString(Wallets.INCOME.resId)
    private val expense = managerUtils.getString(Wallets.EXPENSE.resId)

    private val _refreshFlow = MutableStateFlow(false)
    val refreshFlow = _refreshFlow.asStateFlow()

    private val _statisticsFlow =
        MutableStateFlow<List<ReportAdapter.ItemReport>>(emptyList())
    val statisticsFlow = _statisticsFlow.asStateFlow()

    private val _walletTypesFlow = MutableStateFlow<List<ReportAdapter.ItemReport.ReportTypesWallet>>(
        emptyList()
    )
    val walletTypesFlow = _walletTypesFlow.asStateFlow()

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _refreshFlow.value = true
            val date = dateFlow.value.MMMM_yyyy()
            val allTotalByExpense =
                repository.getWalletsCountByMonthAndYear(type = expense, date = date)

            val data = repository.getStatisticsByTypeAndDate(type = expense, date = date)

            val walletsTypes = data.map { wallet ->
                ReportAdapter.ItemReport.ReportTypesWallet(
                    reportWallet = wallet,
                    percent = (wallet.total.toFloat() / allTotalByExpense) * 100
                )
            }
            _walletTypesFlow.emit(walletsTypes)
            _statisticsFlow.emit(walletsTypes.ifEmpty {
                listOf(ReportAdapter.ItemReport.EmptyReport)
            })

            _refreshFlow.value = false
        }
    }

    fun onChangePreviousOrNextMonth(const: String) {
        _dateFlow.update {
            it.update {
                if (const == HomeFragment.PLUS)
                    plusMonths(1)
                else minusMonths(1)
            }
        }

        onSwipeRefresh()
    }
}