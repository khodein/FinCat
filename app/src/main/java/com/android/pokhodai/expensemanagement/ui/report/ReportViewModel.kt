package com.android.pokhodai.expensemanagement.ui.report

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.service.WalletDao
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val walletDao: WalletDao
): ViewModel() {

    val today = LocalDateFormatter.today()

    init {
        viewModelScope.launch {
            Log.d("TAGAG ", "af "+walletDao.getTotal("Expense", today.MMMM_yyyy()))
        }
    }
}