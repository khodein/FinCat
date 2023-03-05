package com.android.pokhodai.expensemanagement.data.modals

import androidx.room.Ignore
import com.android.pokhodai.expensemanagement.utils.enums.Icons

data class TypesWallet(
    val total: String,
    val sum: String,
    val name: String,
    val icon: Icons,
)