package com.android.pokhodai.expensemanagement.ui.home.creater.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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
                id = it.id ?: 0,
                name = it.name,
                icon = it.icon
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

    fun onDeleteExpenseById(
        id: Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            expenseRepository.deleteById(id)

            _expenseFlow.update {  list ->
                list.toMutableList().apply {
                    val news = find { expense -> expense.id == id }
                    remove(news)
                }
            }
        }
    }
}