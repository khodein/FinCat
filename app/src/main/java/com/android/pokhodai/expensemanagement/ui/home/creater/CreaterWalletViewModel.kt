package com.android.pokhodai.expensemanagement.ui.home.creater

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import com.android.pokhodai.expensemanagement.utils.enums.Wallets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreaterWalletViewModel @Inject constructor(
    managerUtils: ManagerUtils,
    savedStateHandle: SavedStateHandle,
    private val walletRepository: WalletRepository,
    private val userDataSource: UserDataSource,
) : ViewModel() {

    val editOrCreateType = CreaterWalletFragmentArgs.fromSavedStateHandle(savedStateHandle).type
    private val editWallet = CreaterWalletFragmentArgs.fromSavedStateHandle(savedStateHandle).wallet

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.today())
    val dateFlow = _dateFlow.asStateFlow()

    private val _typesWallet =
        MutableStateFlow(managerUtils.getStringArray(R.array.TypesWallet).toList())
    val typesWallet = _typesWallet.asStateFlow()

    val income = managerUtils.getString(Wallets.INCOME.resId)
    val expense = managerUtils.getString(Wallets.EXPENSE.resId)

    private val _typeFlow = MutableStateFlow(income)
    val typeFlow = _typeFlow.asStateFlow()

    private val _categoryNameFlow =
        MutableStateFlow(CategoriesAdapter.Categories(name = "", icon = Icons.NONE, id = 0))
    val categoryNameFlow = _categoryNameFlow.asStateFlow()

    private val _amountFlow = MutableStateFlow("")
    val amountFlow = _amountFlow.asStateFlow()

    private val _descriptionFlow = MutableStateFlow("")
    val descriptionFlow = _descriptionFlow.asStateFlow()

    private val _navigatePopFlow = Channel<Unit>()
    val navigatePopFlow = _navigatePopFlow.receiveAsFlow()

    private val _editWalletFlow =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val editWalletFlow = _editWalletFlow.asSharedFlow()

    init {
        editWallet?.let {
            _dateFlow.value = it.publicatedAt
            _typeFlow.value = it.type
            _amountFlow.value =
                if (it.type == expense) it.amount.toString().drop(1) else it.amount.toString()
            _descriptionFlow.value = it.description
            _categoryNameFlow.value =
                CategoriesAdapter.Categories(name = it.categoryName, icon = it.icons, id = -1)
            _editWalletFlow.tryEmit(Unit)
        }
    }

    val validate = combine(
        _typeFlow,
        _categoryNameFlow,
        _amountFlow,
    ) { type, category, amount ->
        if (editOrCreateType == Creater.CREATE) {
            type.trim().isNotEmpty() && category.name.isNotEmpty() && amount.trim().isNotEmpty()
        } else {
            type.trim().isNotEmpty()
                    && type != editWallet?.type || category.name.isNotEmpty()
                    && category.name != editWallet?.categoryName || amount.trim().isNotEmpty()
                    && amount != editWallet?.amount.toString()
        }

    }

    fun onChangeWalletType(type: String) {
        _typeFlow.value = type
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

            val wallet = WalletEntity(
                id = editWallet?.id,
                categoryName = categoryNameFlow.value.name,
                icons = categoryNameFlow.value.icon,
                amount = if (typeFlow.value == expense) amount - (amount * 2) else amount,
                description = _descriptionFlow.value,
                type = typeFlow.value,
                publicatedAt = dateFlow.value,
                monthAndYear = dateFlow.value.MMMM_yyyy(),
                dateFormat = dateFlow.value.dd_MMMM_yyyy(),
                currency = userDataSource.currency ?: Currency.DOLLAR
            )

            if (editOrCreateType == Creater.CREATE) {
                walletRepository.insertAllWallet(wallet)
            } else {
                walletRepository.updateWallet(wallet)
            }

            _navigatePopFlow.send(Unit)
        }
    }
}