package com.sergei.pokhodai.expensemanagement.data.models

import com.sergei.pokhodai.expensemanagement.utils.enums.Currency
import com.sergei.pokhodai.expensemanagement.utils.enums.Icons

data class ReportWallet(
    val total: Int,
    val sum: String,
    val name: String,
    val icon: Icons,
    val currency: Currency,
)