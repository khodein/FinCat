package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.mapper

import com.sergei.pokhodai.expensemanagement.core.base.utils.getFormatCurrency
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.database.api.entity.EventEntity
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.AmountModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel

internal class EventEntityMapper {

    fun mapModelToEntity(
        userId: Long,
        model: EventModel
    ): EventEntity {
        return EventEntity(
            primaryId = model.id,
            userId = userId,
            budgetType = model.budgetType.name,
            categoryName = model.categoryModel?.name.orEmpty(),
            categoryType = model.categoryModel?.type?.name.orEmpty(),
            categoryColorName = model.categoryModel?.colorName.orEmpty(),
            amountValue = model.amountModel.value.toString(),
            date = model.dateModel.value.yyyy_MM_dd(),
            day = model.dateModel.value.dd(),
            dateMonth = model.dateModel.value.MM(),
            dateYear = model.dateModel.value.yyyy(),
            description = model.description
        )
    }

    fun mapEntityToModel(
        currencyModel: UserCurrencyModel?,
        entity: EventEntity
    ): EventModel {
        return EventModel(
            id = entity.primaryId,
            budgetType = BudgetType.valueOf(entity.budgetType),
            categoryModel = mapCategory(
                categoryName = entity.categoryName,
                categoryType = entity.categoryType,
                categoryColorName = entity.categoryColorName,
            ),
            dateModel = mapDate(date = entity.date),
            amountModel = mapAmount(amountValue = entity.amountValue),
            description = entity.description,
            currencySymbol = currencyModel?.symbol
        )
    }

    private fun mapDate(
        date: String,
    ): DateModel {
        return DateModel(
            value = LocalDateFormatter.parseCalendarFormat(date),
        )
    }

    private fun mapAmount(
        amountValue: String,
    ): AmountModel {
        val format = amountValue.getFormatCurrency()
        return AmountModel(
            value = format.second,
            format = format.first
        )
    }

    private fun mapCategory(
        categoryName: String,
        categoryType: String,
        categoryColorName: String,
    ): CategoryModel {
        return CategoryModel(
            type = CategoryType.valueOf(categoryType),
            name = categoryName,
            colorName = categoryColorName,
            budgetType = null
        )
    }
}