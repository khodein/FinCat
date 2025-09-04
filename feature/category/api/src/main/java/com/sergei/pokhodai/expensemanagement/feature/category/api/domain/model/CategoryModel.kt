package com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model

data class CategoryModel(
    val id: Int? = null,
    val type: CategoryType?,
    val budgetType: BudgetType?,
    val colorName: String,
    val name: String,
) {
    companion object {
        fun getDefault(): CategoryModel {
            return CategoryModel(
                id = null,
                type = null,
                budgetType = null,
                colorName = "",
                name = ""
            )
        }
    }
}