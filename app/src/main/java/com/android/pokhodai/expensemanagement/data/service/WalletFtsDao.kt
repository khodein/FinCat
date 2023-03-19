package com.android.pokhodai.expensemanagement.data.service

import androidx.room.Dao
import androidx.room.Query
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity

@Dao
interface WalletFtsDao {

    @Query("SELECT * FROM wallets JOIN walletsFts ON wallets.categoryName == walletsFts.categoryName AND wallets.id == walletsFts.rowid WHERE walletsFts MATCH :search")
    suspend fun search(search: String): List<WalletEntity>
}