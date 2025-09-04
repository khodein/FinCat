package com.sergei.pokhodai.expensemanagement.feature.category.impl.data.mapper

import com.sergei.pokhodai.expensemanagement.database.api.entity.CategoryEntity
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType

internal class CategoryEntityMapper {

    fun mapEntityToModel(entity: CategoryEntity): CategoryModel {
        return CategoryModel(
            id = entity.primaryId,
            type = CategoryType.valueOf(entity.type),
            colorName = entity.colorName,
            name = entity.name,
            budgetType = BudgetType.valueOf(entity.budgetType)
        )
    }

    fun mapModelToEntity(
        userId: Int,
        model: CategoryModel
    ): CategoryEntity {
        return CategoryEntity(
            primaryId = model.id,
            userId = userId,
            type = model.type?.name.orEmpty(),
            name = model.name,
            colorName = model.colorName,
            budgetType = model.budgetType?.name.orEmpty()
        )
    }
}