package com.android.pokhodai.expensemanagement.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.enums.Icons

@Entity(tableName = "wallets")
data class WalletEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val categoryName: String,
    val icons: Icons,
    val amount: Int,
    val description: String = "",
    val type: String,
    val publicatedAt: LocalDateFormatter,
    val dateFormat: String,
    val monthAndYear: String
)