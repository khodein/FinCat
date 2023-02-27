package com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
): ViewModel() {

    private val _expenseFlow = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenseFlow = _expenseFlow.map { list ->
        list.map {
            CategoriesAdapter.Categories(
                name = it.name,
                resId = it.resId
            )
        }
    }

    init {
        getAllExpenseList()
    }

    private fun getAllExpenseList(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _expenseFlow.emit(expenseRepository.getAllExpense())
        }
    }
}