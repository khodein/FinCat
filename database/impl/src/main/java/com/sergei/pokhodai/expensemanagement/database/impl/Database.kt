package com.sergei.pokhodai.expensemanagement.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergei.pokhodai.expensemanagement.database.api.dao.CategoryDao
import com.sergei.pokhodai.expensemanagement.database.api.dao.EventDao
import com.sergei.pokhodai.expensemanagement.database.api.dao.UserDao
import com.sergei.pokhodai.expensemanagement.database.api.entity.CategoryEntity
import com.sergei.pokhodai.expensemanagement.database.api.entity.EventEntity
import com.sergei.pokhodai.expensemanagement.database.api.entity.UserSelfEntity

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        EventEntity::class,
        UserSelfEntity::class
    ],
)
internal abstract class Database : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getEventDao(): EventDao
    abstract fun getUserDao(): UserDao
}