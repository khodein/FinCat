package com.android.pokhodai.expensemanagement.data.models

import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.android.pokhodai.expensemanagement.utils.enums.Icons

data class ReportWallet(
    val total: Int,
    val sum: String,
    val name: String,
    val icon: Icons,
    val currency: Currency,
)