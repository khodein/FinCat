package com.android.pokhodai.expensemanagement.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.pokhodai.expensemanagement.utils.enums.Icons

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val name: String,
    val icon: Icons,
    val position: Int
)