package com.android.pokhodai.expensemanagement.ui.home.add_wallet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewWalletViewModel @Inject constructor(
    managerUtils: ManagerUtils,
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.today())
    val dateFlow = _dateFlow.asStateFlow()

    private val _typesWallet = MutableStateFlow(managerUtils.getStringArray(R.array.TypesWallet).toList())
    val typesWallet = _typesWallet.asStateFlow()

    val incomeText = managerUtils.getString(R.string.add_new_wallet_income)

    private val _walletTypeFlow = MutableStateFlow(incomeText)
    val walletTypeFlow = _walletTypeFlow.asStateFlow()

    private val _categoryNameFlow = MutableStateFlow(CategoriesAdapter.Categories(name = "", Icons.NONE))
    val categoryNameFlow = _categoryNameFlow.asStateFlow()

    private val _amountFlow = MutableStateFlow("")

    private val _descriptionFlow = MutableStateFlow("")

    private val _navigatePopFlow = Channel<Unit>()
    val navigatePopFlow = _navigatePopFlow.receiveAsFlow()

    val validate = combine(
        _walletTypeFlow,
        _categoryNameFlow,
        _amountFlow,
    ) { type, category, amount ->
        type.trim().isNotEmpty() && category.name.isNotEmpty() && amount.trim().isNotEmpty()
    }

    fun onChangeWalletType(type: String) {
        _walletTypeFlow.value = type
    }

    fun onChangeCategoryName(name: CategoriesAdapter.Categories) {
        _categoryNameFlow.value = name
    }

    fun onChangeAmount(amount: String) {
        _amountFlow.value = amount
    }

    fun onChangeDescription(description: String) {
        _descriptionFlow.value = description
    }

    fun onChangeDate(date: LocalDateFormatter) {
        _dateFlow.value = date
    }

    fun onAddNewWallet(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            val amount = Integer.parseInt(_amountFlow.value)

            walletRepository.insertAllWallet(
                WalletEntity(
                    categoryName = categoryNameFlow.value.name,
                    icons = categoryNameFlow.value.icon,
                    amount = if (walletTypeFlow.value == "Expense") amount - (amount*2) else amount,
                    description = _descriptionFlow.value,
                    type = walletTypeFlow.value,
                    publicatedAt = dateFlow.value,
                    monthAndYear = dateFlow.value.MMMM_yyyy(),
                    dateFormat = dateFlow.value.dd_MMMM_yyyy()
                )
            )

            _navigatePopFlow.send(Unit)
        }
    }
}