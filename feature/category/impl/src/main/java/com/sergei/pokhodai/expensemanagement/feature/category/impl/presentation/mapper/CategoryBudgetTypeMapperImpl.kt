package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryBudgetTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R

internal class CategoryBudgetTypeMapperImpl(
    private val resManager: ResManager
) : CategoryBudgetTypeMapper {

    override fun getBudgetName(budgetType: BudgetType): String {
        val resId = when (budgetType) {
            BudgetType.INCOME -> R.string.category_manager_income
            BudgetType.EXPENSE -> R.string.category_manager_expense
        }

        return resManager.getString(resId)
    }
}