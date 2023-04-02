package com.android.pokhodai.expensemanagement.ui.settings.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.ui.settings.manager.adapter.ManagerCategoriesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ManagerCategoriesViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expensesFlow = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expensesFlow = _expensesFlow.map {
        it.map { expense ->
            ManagerCategoriesAdapter.ItemManagerCategories.WrapManagerCategory(
                expense
            )
        }.ifEmpty { listOf(ManagerCategoriesAdapter.ItemManagerCategories.ItemEmpty) }
    }

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            _expensesFlow.emit(expenseRepository.getAllExpense())
        }
    }

    fun onDeleteExpense(
        position: Int,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            expenseRepository.deleteExpense(_expensesFlow.value[position])
            _expensesFlow.update {
                val list = it.toMutableList()
                list.removeAt(position)
                list
            }
        }
    }

    fun onSwapAndUpdateExpenses(
        oldPosition: Int,
        newPosition: Int,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            Collections.swap(_expensesFlow.value, oldPosition, newPosition)
            _expensesFlow.value.forEachIndexed { index, expenseEntity ->
                expenseRepository.updateExpense(expenseEntity.copy(position = index))
            }
        }
    }
}