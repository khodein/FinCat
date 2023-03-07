package com.android.pokhodai.expensemanagement.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg expenses: ExpenseEntity)

    @Query("SELECT EXISTS(SELECT * FROM expenses WHERE name = :nameExpense)")
    suspend fun checkNameExpense(nameExpense: String): Boolean

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpenseById(id: Int)
}