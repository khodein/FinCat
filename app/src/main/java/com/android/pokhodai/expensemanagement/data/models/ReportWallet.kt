package com.android.pokhodai.expensemanagement.data.models

import com.android.pokhodai.expensemanagement.utils.enums.Icons

abstract class Report {
    abstract val total: Int
    abstract val sum: String
    abstract val name: String
    abstract val icon: Icons
}

data class ReportWallet(
    override val total: Int,
    override val sum: String,
    override val name: String,
    override val icon: Icons,
): Report()