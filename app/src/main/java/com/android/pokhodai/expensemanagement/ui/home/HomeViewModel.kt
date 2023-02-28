package com.android.pokhodai.expensemanagement.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.now())
    val dateFlow = _dateFlow.asStateFlow()

    init {

//        var date = LocalDateFormatter.now().update { minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()) }
//        viewModelScope.launch {
//            repeat(100) {
//                walletRepository.insertAllWallet(
//                    WalletEntity(
//                        categoryName = "123",
//                        icons = Icons.ALLOWANCE,
//                        amount = 2123,
//                        type = "Income",
//                        publicatedAt = date.timeInMillis(),
//                        monthAndYear = date.MMMM_yyyy()
//                    )
//                )
//
//                date = date.update { plusDays(1) }
//            }
//        }

//        Log.d("TAGATAG ", "tag "+walletRepository.getAllWallets())
    }

    fun onChangePreviousOrNextMonth(const: String, dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            val date = dateFlow.value.update {
                if (const == HomeFragment.PLUS) plusMonths(1) else minusMonths(1)
            }
            Log.d("TAGATGATG ", "tagtag "+walletRepository.getWalletByMonth(date.MMMM_yyyy()))
            _dateFlow.update { date }
        }
    }

    fun onChangeMonthDate(date: LocalDateFormatter) {
        _dateFlow.update { date }
    }
}