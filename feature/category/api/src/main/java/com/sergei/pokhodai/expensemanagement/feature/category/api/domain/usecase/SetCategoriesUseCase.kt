package com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

interface SetCategoriesUseCase {
    suspend operator fun invoke(vararg models: CategoryModel)
}