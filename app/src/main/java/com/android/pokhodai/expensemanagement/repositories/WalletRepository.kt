package com.android.pokhodai.expensemanagement.repositories

import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.data.service.WalletDao
import javax.inject.Inject

class WalletRepository @Inject constructor(
    private val walletDao: WalletDao
) {
    fun getAllWallets() = walletDao.getAll()

    fun insertAllWallet(vararg wallets: WalletEntity) {
        walletDao.insertAll(*wallets)
    }
}