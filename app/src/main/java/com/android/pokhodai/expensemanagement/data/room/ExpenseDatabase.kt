package com.android.pokhodai.expensemanagement.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.data.service.ExpenseDao
import com.android.pokhodai.expensemanagement.data.service.WalletDao

@Database(entities = [WalletEntity::class, ExpenseEntity::class], version = 3)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun walletDao(): WalletDao

    abstract fun expenseDao(): ExpenseDao

    companion object{
        const val DATABASE_NAME: String = "expense_db"
    }
}