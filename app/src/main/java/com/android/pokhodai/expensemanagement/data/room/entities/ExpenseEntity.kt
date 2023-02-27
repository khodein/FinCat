package com.android.pokhodai.expensemanagement.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val name: String,
    val resId: Int
)