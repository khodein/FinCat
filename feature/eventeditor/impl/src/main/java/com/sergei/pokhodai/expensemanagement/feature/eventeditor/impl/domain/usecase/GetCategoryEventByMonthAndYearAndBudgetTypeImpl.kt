package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetCategoryEventByMonthAndYearAndBudgetTypeUceCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.EventRepository

internal class GetCategoryEventByMonthAndYearAndBudgetTypeImpl(
    private val eventRepository: EventRepository,
) : GetCategoryEventByMonthAndYearAndBudgetTypeUceCase {
    override suspend fun invoke(
        date: LocalDateFormatter,
        budgetType: BudgetType
    ): Map<CategoryModel, List<EventModel>> {
        return eventRepository.getCategoryEventByMonthAndYearAndBudgetType(
            date = date,
            budgetType = budgetType
        )
    }
}