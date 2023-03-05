package com.android.pokhodai.expensemanagement.data.service

import androidx.paging.DataSource
import androidx.room.*
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallets")
    suspend fun getAll(): List<WalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg expenses: WalletEntity)

    @Query("SELECT * FROM wallets WHERE monthAndYear LIKE :date ORDER BY publicatedAt ASC LIMIT :limit OFFSET :offset")
    suspend fun getWalletByMonth(date: String, limit: Int, offset: Int): List<WalletEntity>

    @Query("SELECT SUM(amount) AS value FROM wallets WHERE type LIKE :type AND monthAndYear LIKE :date")
    suspend fun sumAmountByType(type: String, date: String): Int

    @Query("SELECT SUM(amount) AS value FROM wallets WHERE dateFormat LIKE :date")
    suspend fun sumAmountByPublicateAt(date: String): Int

    @Query("SELECT COUNT(id) FROM wallets")
    suspend fun getWalletsCount(): Int

    @Query("DELETE FROM wallets WHERE id = :id")
    suspend fun deleteWalletById(id: Int)
}