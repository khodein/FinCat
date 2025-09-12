package com.sergei.pokhodai.expensemanagement.feature.category.api.mapper

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType

interface CategoryBudgetTypeMapper {
    fun getBudgetName(budgetType: BudgetType): String
}