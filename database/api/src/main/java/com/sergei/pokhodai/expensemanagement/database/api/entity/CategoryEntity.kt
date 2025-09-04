package com.sergei.pokhodai.expensemanagement.database.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergei.pokhodai.expensemanagement.database.api.key.DatabaseKey

@Entity(tableName = "${DatabaseKey.DATABASE_NAME}_Category_Table")
class CategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val primaryId: Int? = null,
    @ColumnInfo("userId") val userId: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("colorName") val colorName: String,
    @ColumnInfo("budgetType") val budgetType: String,
)