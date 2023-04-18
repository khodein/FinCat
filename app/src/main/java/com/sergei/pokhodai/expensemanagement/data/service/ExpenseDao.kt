package com.sergei.pokhodai.expensemanagement.data.service

import androidx.room.*
import com.sergei.pokhodai.expensemanagement.data.room.entities.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses ORDER BY position ASC")
    suspend fun getAll(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg expenses: ExpenseEntity)

    @Query("SELECT EXISTS(SELECT * FROM expenses WHERE name = :nameExpense)")
    suspend fun checkNameExpense(nameExpense: String): Boolean

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpenseById(id: Int)

    @Update
    suspend fun update(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun delete(expenseEntity: ExpenseEntity)

    @Query("SELECT COUNT(id) FROM expenses")
    suspend fun getCount(): Int

    @Query("DELETE FROM expenses")
    suspend fun deleteAll()
}