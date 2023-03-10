package com.android.pokhodai.expensemanagement.data.room.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wallets")
@Parcelize
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
): Parcelable