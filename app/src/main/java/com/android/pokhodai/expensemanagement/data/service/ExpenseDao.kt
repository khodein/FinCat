package com.android.pokhodai.expensemanagement.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    fun getAll(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg expenses: ExpenseEntity)
}