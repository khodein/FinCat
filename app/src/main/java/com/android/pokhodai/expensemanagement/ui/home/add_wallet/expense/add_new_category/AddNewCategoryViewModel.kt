package com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category

import android.graphics.drawable.Icon
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewCategoryViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _categoryNameFlow = MutableStateFlow("")

    private val _iconResIdFlow = MutableStateFlow(Icons.NONE)
    val iconResIdFlow = _iconResIdFlow.asStateFlow()

    private val _navigatePopFlow = Channel<Unit>()
    val navigatePopFlow = _navigatePopFlow.receiveAsFlow()

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

    fun onAddNewCategory(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            expenseRepository.insertAllExpense(
                ExpenseEntity(
                    name = _categoryNameFlow.value,
                    icon = iconResIdFlow.value
                )
            )

            _navigatePopFlow.send(Unit)
        }
    }
}