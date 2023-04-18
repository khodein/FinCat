package com.sergei.pokhodai.expensemanagement.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.data.service.WalletDao
import com.sergei.pokhodai.expensemanagement.ui.home.source.WalletPagingSource
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val walletDao: WalletDao
) {
    suspend fun getAllWallets() = walletDao.getAll()

    suspend fun insertAllWallet(vararg wallets: WalletEntity) = walletDao.insertAll(*wallets)

    suspend fun sumByType(type: String, date: String) = walletDao.sumAmountByType(type, date)

    suspend fun sumByPublicatedAt(dateFormat: String) = walletDao.sumAmountByPublicateAt(dateFormat)

    suspend fun deleteWalletById(id: Int) = walletDao.deleteWalletById(id)

    suspend fun getStatisticsByTypeAndDate(type: String, date: String) =
        walletDao.getStatisticsByTypeAndDate(type, date)

    suspend fun getWalletsCountByMonthAndYear(type: String, date: String) =
        walletDao.getWalletsCountByMonthAndYear(type, date)

    suspend fun updateWallet(walletEntity: WalletEntity) {
        walletDao.update(walletEntity)
    }

    suspend fun deleteAll() = walletDao.deleteAll()

    suspend fun findCategoryNames(categoryNames: Array<String>) = walletDao.find(categoryNames)

    suspend fun findCategoryNamesByGroup() = walletDao.findCategoryNamesByGroup()

    fun getPaginationWalletsByDate(date: String) = Pager(
        PagingConfig(
            pageSize = 30,
            initialLoadSize = 30,
            prefetchDistance = 5,
            maxSize = 60,
            enablePlaceholders = false
        )
    ) {
        WalletPagingSource(date = date, walletDao = walletDao)
    }
}