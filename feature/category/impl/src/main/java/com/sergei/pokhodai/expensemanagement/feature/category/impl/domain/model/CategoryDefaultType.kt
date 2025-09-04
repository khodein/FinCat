package com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.model

import androidx.annotation.StringRes
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R

internal enum class CategoryDefaultType(
    val type: CategoryType,
    val budgetType: BudgetType,
    val colorName: String,
    @StringRes val nameResId: Int
) {
    SALARY(
        type = CategoryType.MONEY,
        budgetType = BudgetType.INCOME,
        colorName = "#FFCCBC",
        nameResId = R.string.default_category_income_salary
    ),

    GIFT_INCOME(
        type = CategoryType.GIFTS,
        budgetType = BudgetType.INCOME,
        colorName = "#E1BEE7",
        nameResId = R.string.default_category_income_gift
    ),

    WAGES(
        type = CategoryType.DONATE,
        budgetType = BudgetType.INCOME,
        colorName = "#FFF9C4",
        nameResId = R.string.default_category_income_wages
    ),

    INTEREST(
        type = CategoryType.INSTITUTE,
        budgetType = BudgetType.INCOME,
        colorName = "#FFE0B2",
        nameResId = R.string.default_category_income_interest
    ),

    SAVINGS(
        type = CategoryType.SAVINGS,
        budgetType = BudgetType.INCOME,
        colorName = "#C5CAE9",
        nameResId = R.string.default_category_income_savings
    ),

    ALLOWANCE(
        type = CategoryType.MONEY,
        budgetType = BudgetType.INCOME,
        colorName = "#C8E6C9",
        nameResId = R.string.default_category_income_allowance
    ),

    GROCERY(
        type = CategoryType.GROCERIES,
        budgetType = BudgetType.EXPENSE,
        colorName = "#C8E6C9",
        nameResId = R.string.default_category_expense_grocery
    ),

    GIFT_EXPENSE(
        type = CategoryType.GIFTS,
        budgetType = BudgetType.EXPENSE,
        colorName = "#E1BEE7",
        nameResId = R.string.default_category_expense_gift
    ),

    BAR(
        type = CategoryType.CAFE,
        budgetType = BudgetType.EXPENSE,
        colorName = "#FFECB3",
        nameResId = R.string.default_category_expense_bar
    ),

    HEALTH(
        type = CategoryType.HEALTH,
        budgetType = BudgetType.EXPENSE,
        colorName = "#F8BBD0",
        nameResId = R.string.default_category_expense_health
    ),

    COMMUTE(
        type = CategoryType.TRANSPORTATION,
        budgetType = BudgetType.EXPENSE,
        colorName = "#B2EBF2",
        nameResId = R.string.default_category_expense_commute
    ),

    ELECTRONICS(
        type = CategoryType.ELECTRONICS,
        budgetType = BudgetType.EXPENSE,
        colorName = "#FFCDD2",
        nameResId = R.string.default_category_expense_electronics
    ),

    LAUNDRY(
        type = CategoryType.LAUNDRY,
        budgetType = BudgetType.EXPENSE,
        colorName = "#B3E5FC",
        nameResId = R.string.default_category_expense_laundry,
    ),

    LIQUOR(
        type = CategoryType.LIQUOR,
        budgetType = BudgetType.EXPENSE,
        colorName = "#DCEDC8",
        nameResId = R.string.default_category_expense_liquor
    ),

    RESTAURANT(
        type = CategoryType.RESTAURANT,
        budgetType = BudgetType.EXPENSE,
        colorName = "#C5CAE9",
        nameResId = R.string.default_category_expense_restaurant
    )
}