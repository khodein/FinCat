package com.sergei.pokhodai.expensemanagement.ui.home.creater

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.repositories.WalletRepository
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.utils.ManagerUtils
import com.sergei.pokhodai.expensemanagement.utils.enums.Creater
import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.enums.Icons
import com.sergei.pokhodai.expensemanagement.utils.enums.Wallets
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
    languageRepository: LanguageRepository
) : ViewModel() {

    val editOrCreateType = CreaterWalletFragmentArgs.fromSavedStateHandle(savedStateHandle).type
    private var editWallet = CreaterWalletFragmentArgs.fromSavedStateHandle(savedStateHandle).wallet

    private val language = languageRepository.getLanguage()

    private val _dateFlow = MutableStateFlow(LocalDateFormatter.today())
    val dateFlow = _dateFlow.asStateFlow()

    private val _typesWalletFlow =
        MutableStateFlow(R.array.TypesWallet)
    val typesWalletFlow = _typesWalletFlow.asStateFlow()

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
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val editWalletFlow = _editWalletFlow.asSharedFlow()

    init {
        editWallet?.let {
            _dateFlow.value = it.publicatedAt
            _typeFlow.value = it.type
            _amountFlow.value = it.amount.toString().filter { amount -> amount.isDigit() }
            _descriptionFlow.value = it.description
            _categoryNameFlow.value =
                CategoriesAdapter.Categories(
                    name = it.categoryName,
                    icon = it.icons,
                    id = -1
                )
            _editWalletFlow.tryEmit(Unit)
        }
    }

    val validate = combine(
        _typeFlow,
        _categoryNameFlow,
        _amountFlow,
        _dateFlow,
        _descriptionFlow
    ) { type, category, amount, date, description ->
        if (editOrCreateType == Creater.CREATE) {
            type.isNotEmpty() && category.name.isNotEmpty() && amount.isNotEmpty()
        } else {
            category.name != editWallet?.categoryName
                    || date != editWallet?.publicatedAt
                    || description != editWallet?.description
                    || amount != editWallet?.amount.toString()
        }
    }

    fun onChangeWalletType(type: String) {
        _typeFlow.value = type
        _amountFlow.value = ""
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
            val amount = if (typeFlow.value == income) {
                amountFlow.value
            } else {
                "-${amountFlow.value}"
            }.toLong()

            val wallet = WalletEntity(
                id = editWallet?.id,
                categoryName = categoryNameFlow.value.name,
                icons = categoryNameFlow.value.icon,
                amount = amount,
                description = _descriptionFlow.value,
                type = typeFlow.value,
                publicatedAt = dateFlow.value,
                monthAndYear = dateFlow.value.MMMM_yyyy(language = language),
                dateFormat = dateFlow.value.dd_MMMM_yyyy(language = language),
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