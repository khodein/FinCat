package com.android.pokhodai.expensemanagement.repositories

import com.android.pokhodai.expensemanagement.data.service.WalletFtsDao
import javax.inject.Inject

class WalletFtsRepository @Inject constructor(
    private val walletFtsDao: WalletFtsDao
) {

    suspend fun searchByWallet(search: String) = walletFtsDao.search(search)
}