package com.sergei.pokhodai.expensemanagement.feature.category.impl.data

import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.model.CategoryDefaultType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

internal class CategoryDefaultRepository {
    fun getDefaultList(): List<CategoryModel> {
        return CategoryDefaultType.entries.map {
            CategoryModel(
                type = it.type,
                colorName = it.colorName,
                name = it.name,
                budgetType = it.budgetType
            )
        }
    }
}