package com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel

interface GetCategoryEventByMonthAndYearAndBudgetTypeUceCase {
    suspend operator fun invoke(
        date: LocalDateFormatter,
        budgetType: BudgetType,
    ): Map<CategoryModel, List<EventModel>>
}