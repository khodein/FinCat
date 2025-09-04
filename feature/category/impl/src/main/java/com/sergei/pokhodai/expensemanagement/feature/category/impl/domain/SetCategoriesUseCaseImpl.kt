package com.sergei.pokhodai.expensemanagement.feature.category.impl.domain

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository

internal class SetCategoriesUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : SetCategoriesUseCase {
    override suspend fun invoke(vararg models: CategoryModel) {
        categoryRepository.setCategories(models = models)
    }
}