package com.android.pokhodai.expensemanagement.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class WalletEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val categoryName: String,
    val resId: Int,
    val amount: Int,
    val description: String = "",
    val type: String
)