package com.android.pokhodai.expensemanagement.di

import android.content.Context
import androidx.room.Room
import com.android.pokhodai.expensemanagement.data.room.ExpenseDatabase
import com.android.pokhodai.expensemanagement.data.service.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideExpenseDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            ExpenseDatabase.DATABASE_NAME
        )
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideWalletDao(expenseDatabase: ExpenseDatabase) = expenseDatabase.walletDao()

    @Singleton
    @Provides
    fun provideExpenseDao(expenseDatabase: ExpenseDatabase) = expenseDatabase.expenseDao()
}