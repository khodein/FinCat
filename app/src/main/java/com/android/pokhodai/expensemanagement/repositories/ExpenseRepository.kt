package com.android.pokhodai.expensemanagement.repositories

import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.data.service.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    fun getAllExpense() = expenseDao.getAll()

    fun insertAllExpense(vararg expenses: ExpenseEntity) {
        expenseDao.insertAll(*expenses)
    }
}