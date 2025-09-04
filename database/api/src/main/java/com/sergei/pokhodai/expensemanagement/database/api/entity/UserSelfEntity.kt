package com.sergei.pokhodai.expensemanagement.database.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergei.pokhodai.expensemanagement.database.api.key.DatabaseKey

@Entity(tableName = "${DatabaseKey.DATABASE_NAME}_User_Table")
class UserSelfEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val primaryId: Int? = null,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("currency") val currency: String,
)