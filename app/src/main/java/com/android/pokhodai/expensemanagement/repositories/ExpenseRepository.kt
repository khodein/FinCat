package com.android.pokhodai.expensemanagement.repositories

import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.data.service.ExpenseDao
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {

    suspend fun getAllExpense() = expenseDao.getAll()

    suspend fun insertAllExpense(vararg expenses: ExpenseEntity) {
        expenseDao.insertAll(*expenses)
    }

    suspend fun checkNameExpense(name: String) = expenseDao.checkNameExpense(name)

    suspend fun deleteById(id: Int) = expenseDao.deleteExpenseById(id)

    suspend fun updateExpense(expenseEntity: ExpenseEntity) = expenseDao.update(expenseEntity)

    suspend fun deleteExpense(expenseEntity: ExpenseEntity) = expenseDao.delete(expenseEntity)

    suspend fun getCount() = expenseDao.getCount()
}