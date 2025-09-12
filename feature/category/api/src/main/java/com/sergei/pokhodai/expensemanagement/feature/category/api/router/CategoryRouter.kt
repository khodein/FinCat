package com.sergei.pokhodai.expensemanagement.feature.category.api.router

interface CategoryRouter {
    fun goToCategoryEditor(
        id: Long? = null,
        budgetType: String? = null,
        isOpenFromDialog: Boolean = false
    )

    fun goToCategoryDialog(budgetType: String)

    fun goToCategoryIcon()
    fun goToCategoryManager()
}