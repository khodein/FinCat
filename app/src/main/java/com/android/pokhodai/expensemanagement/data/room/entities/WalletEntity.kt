package com.android.pokhodai.expensemanagement.data.room.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wallets")
@Parcelize
data class WalletEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val categoryName: String,
    val icons: Icons,
    val amount: Long,
    val description: String = "",
    val type: String,
    val publicatedAt: LocalDateFormatter,
    val dateFormat: String,
    val monthAndYear: String,
    val currency: Currency,
): Parcelable

@Fts4(contentEntity = WalletEntity::class)
@Entity(tableName = "walletsFts")
data class WalletFtsEntity(
    @PrimaryKey
    @ColumnInfo(name = "rowid") val id: Int,
    val categoryName: String,
    val description: String,
)