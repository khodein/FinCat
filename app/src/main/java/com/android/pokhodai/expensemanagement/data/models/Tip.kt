package com.android.pokhodai.expensemanagement.data.models

import com.android.pokhodai.expensemanagement.utils.enums.Icons

data class Tip(
    override val name: String,
    override val icon: Icons,
): Category()

data class CategoryTip(
    override val name: String,
    override val icon: Icons,
    val isFind: Boolean = false
): Category()

abstract class Category {
    abstract val name: String
    abstract val icon: Icons
}

