package com.android.pokhodai.expensemanagement.data.room.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import kotlinx.parcelize.Parcelize

@Entity(tableName = "expenses")
@Parcelize
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val name: String,
    val icon: Icons,
    val position: Int
): Parcelable