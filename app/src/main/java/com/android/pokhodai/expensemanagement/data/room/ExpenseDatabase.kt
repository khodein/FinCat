package com.android.pokhodai.expensemanagement.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.data.service.WalletDao

@Database(entities = [WalletEntity::class ], version = 1)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun walletDao(): WalletDao

    companion object{
        const val DATABASE_NAME: String = "expense_db"
    }
}