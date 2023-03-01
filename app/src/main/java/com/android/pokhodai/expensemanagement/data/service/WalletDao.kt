package com.android.pokhodai.expensemanagement.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallets")
    fun getAll(): List<WalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg expenses: WalletEntity)

    @Query("SELECT * FROM wallets WHERE monthAndYear LIKE :date")
    fun getWalletByMonth(date: String): List<WalletEntity>

    @Query("SELECT SUM(amount) AS value FROM wallets WHERE type LIKE :type AND monthAndYear LIKE :date")
    fun sumAmountByType(type: String, date: String): Int

    @Query("DELETE FROM wallets WHERE id = :id")
    fun deleteWalletById(id: Int)
}