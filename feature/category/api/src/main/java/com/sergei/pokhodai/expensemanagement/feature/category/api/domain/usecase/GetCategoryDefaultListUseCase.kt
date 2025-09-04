package com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

interface GetCategoryDefaultListUseCase {
    suspend fun invoke(): List<CategoryModel>
}