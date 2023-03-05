package com.android.pokhodai.expensemanagement.repositories

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.data.service.WalletDao
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val walletDao: WalletDao
) {
    suspend fun getAllWallets() = walletDao.getAll()

    suspend fun insertAllWallet(vararg wallets: WalletEntity) = walletDao.insertAll(*wallets)

    suspend fun sumByType(type: String, date: String) = walletDao.sumAmountByType(type, date)

    suspend fun sumByPublicatedAt(dateFormat: String) = walletDao.sumAmountByPublicateAt(dateFormat)

    suspend fun deleteWalletById(id: Int) = walletDao.deleteWalletById(id)

    suspend fun getWalletsCount() = walletDao.getWalletsCount()
}