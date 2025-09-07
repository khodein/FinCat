package com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

data class EventModel(
    val id: Long? = null,
    val budgetType: BudgetType,
    val categoryModel: CategoryModel?,
    val dateModel: DateModel,
    val amountModel: AmountModel,
    val description: String,
) {
    companion object {
        fun getDefault(): EventModel {
            return EventModel(
                dateModel = DateModel(
                    value = LocalDateFormatter.today()
                ),
                description = "",
                categoryModel = null,
                budgetType = BudgetType.INCOME,
                amountModel = AmountModel.getDefault()
            )
        }
    }
}