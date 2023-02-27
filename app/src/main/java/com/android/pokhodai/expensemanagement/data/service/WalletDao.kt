package com.android.pokhodai.expensemanagement.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallets")
    fun getAll(): List<WalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg expenses: WalletEntity)
}