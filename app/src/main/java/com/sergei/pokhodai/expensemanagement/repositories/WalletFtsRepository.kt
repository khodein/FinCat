package com.sergei.pokhodai.expensemanagement.repositories

import com.sergei.pokhodai.expensemanagement.data.service.WalletFtsDao
import javax.inject.Inject

class WalletFtsRepository @Inject constructor(
    private val walletFtsDao: WalletFtsDao
) {
    suspend fun searchByWallet(search: String) = walletFtsDao.search(search)
}