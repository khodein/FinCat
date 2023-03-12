package com.android.pokhodai.expensemanagement.ui.home.creater.expense.creater_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.ui.home.creater.CreaterWalletFragmentArgs
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreaterCategoryViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val editOrCreateType = CreaterCategoryFragmentArgs.fromSavedStateHandle(savedStateHandle).type
    val editExpense = CreaterCategoryFragmentArgs.fromSavedStateHandle(savedStateHandle).expense

    private val _categoryNameFlow = MutableStateFlow("")

    private val _iconResIdFlow = MutableStateFlow(Icons.NONE)
    val iconResIdFlow = _iconResIdFlow.asStateFlow()

    private val _snackBarFlow = Channel<Unit>()
    val shackBarFlow = _snackBarFlow.receiveAsFlow()

    private val _navigatePopFlow = Channel<Unit>()
    val navigatePopFlow = _navigatePopFlow.receiveAsFlow()

    private val _updateFlow =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val updateFlow = _updateFlow.asSharedFlow()

    init {
        editExpense?.let {
            _categoryNameFlow.value = it.name
            _iconResIdFlow.value = it.icon
            _updateFlow.tryEmit(Unit)
        }
    }

    val validate = combine(
        _iconResIdFlow,
        _categoryNameFlow
    ) { icon, name ->
        icon != Icons.NONE && name.trim().isNotEmpty()
    }

    fun onChangeCategoryName(name: String) {
        _categoryNameFlow.value = name
    }

    fun onChangeIcon(icon: Icons) {
        _iconResIdFlow.value = icon
    }

    private fun onUpdateCategory(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            expenseRepository.updateExpense(
                ExpenseEntity(
                    editExpense?.id,
                    _categoryNameFlow.value,
                    _iconResIdFlow.value,
                    editExpense?.position ?: 0
                )
            )

            _navigatePopFlow.send(Unit)
        }
    }

    fun onClickCreater() {
        if (editOrCreateType == Creater.CREATE) {
            onAddNewCategory()
        } else {
            onUpdateCategory()
        }
    }

    private fun onAddNewCategory(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            val count = expenseRepository.getCount() - 1
            val position = if (count < 0) 0 else count + 1
            val isCheck = expenseRepository.checkNameExpense(_categoryNameFlow.value)
            if (isCheck) {
                _snackBarFlow.send(Unit)
                return@launch
            }
            expenseRepository.insertAllExpense(
                ExpenseEntity(
                    name = _categoryNameFlow.value,
                    icon = iconResIdFlow.value,
                    position = position
                )
            )

            _navigatePopFlow.send(Unit)
        }
    }
}