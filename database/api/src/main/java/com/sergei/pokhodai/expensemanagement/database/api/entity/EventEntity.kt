package com.sergei.pokhodai.expensemanagement.database.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergei.pokhodai.expensemanagement.database.api.key.DatabaseKey

@Entity(tableName = "${DatabaseKey.DATABASE_NAME}_Event_Table")
class EventEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val primaryId: Int? = null,
    @ColumnInfo("userId") val userId: Int,
    @ColumnInfo("budgetType") val budgetType: String,
    @ColumnInfo("categoryType") val categoryType: String,
    @ColumnInfo("categoryColorName") val categoryColorName: String,
    @ColumnInfo("categoryName") val categoryName: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("amountValue") val amountValue: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("dateDay") val day: String,
    @ColumnInfo("dateMonth") val dateMonth: String,
    @ColumnInfo("dateYear") val dateYear: String,
)