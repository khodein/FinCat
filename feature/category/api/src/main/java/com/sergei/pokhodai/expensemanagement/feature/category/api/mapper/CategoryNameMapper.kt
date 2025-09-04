package com.sergei.pokhodai.expensemanagement.feature.category.api.mapper

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

interface CategoryNameMapper {
    fun getName(model: CategoryModel?): String
    fun getName(name: String?): String
}