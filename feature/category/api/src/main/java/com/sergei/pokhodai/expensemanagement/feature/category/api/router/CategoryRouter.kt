package com.sergei.pokhodai.expensemanagement.feature.category.api.router

interface CategoryRouter {
    fun goToCategoryEditor(
        id: Int? = null,
        budgetType: String,
        isOpenFromDialog: Boolean = false
    )

    fun goToCategoryDialog(budgetType: String)

    fun goToCategoryIcon()
}