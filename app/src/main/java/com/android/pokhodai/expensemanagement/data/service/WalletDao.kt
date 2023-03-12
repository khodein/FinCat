package com.android.pokhodai.expensemanagement.data.service

import androidx.room.*
import com.android.pokhodai.expensemanagement.data.models.ReportWallet
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity

@Dao
interface WalletDao {

    @Query("SELECT * FROM wallets")
    suspend fun getAll(): List<WalletEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg expenses: WalletEntity)

    @Query("SELECT * FROM wallets WHERE monthAndYear LIKE :date ORDER BY publicatedAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getWalletByMonth(date: String, limit: Int, offset: Int): List<WalletEntity>

    @Query("SELECT SUM(amount) AS value FROM wallets WHERE type LIKE :type AND monthAndYear LIKE :date")
    suspend fun sumAmountByType(type: String, date: String): Int

    @Query("SELECT SUM(amount) AS value FROM wallets WHERE dateFormat LIKE :date")
    suspend fun sumAmountByPublicateAt(date: String): Int

    @Query("SELECT COUNT(id) FROM wallets WHERE monthAndYear LIKE :date AND type LIKE :type")
    suspend fun getWalletsCountByMonthAndYear(type: String, date: String): Int

    @Query("DELETE FROM wallets WHERE id = :id")
    suspend fun deleteWalletById(id: Int)

    @Query("SELECT categoryName, COUNT(categoryName) as total, SUM(amount) as sum, categoryName as name, icons as icon, currency as currency FROM wallets WHERE monthAndYear LIKE :date AND type LIKE :type GROUP BY categoryName")
    suspend fun getStatisticsByTypeAndDate(type: String, date: String): List<ReportWallet>

    @Update(entity = WalletEntity::class)
    suspend fun update(wallet: WalletEntity)

    @Query("DELETE FROM wallets")
    suspend fun deleteAll()
}