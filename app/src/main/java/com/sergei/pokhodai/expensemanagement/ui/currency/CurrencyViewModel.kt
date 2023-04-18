package com.sergei.pokhodai.expensemanagement.ui.currency

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val userDataSource: UserDataSource
): ViewModel() {

    private val currency = userDataSource.currency ?: Currency.DOLLAR

    private val _currencyFlow = MutableStateFlow(currency)
    val currencyFlow = _currencyFlow.asStateFlow()

    val validateFlow = _currencyFlow.map {
        it != currency
    }

    fun onChangeCurrency(currency: Currency) {
        _currencyFlow.value = currency
    }

    fun onClickCurrency() {
        userDataSource.currency = currencyFlow.value
    }
}