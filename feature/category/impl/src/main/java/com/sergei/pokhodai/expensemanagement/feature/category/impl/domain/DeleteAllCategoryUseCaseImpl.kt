package com.sergei.pokhodai.expensemanagement.feature.category.impl.domain

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.DeleteAllCategoryUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository

internal class DeleteAllCategoryUseCaseImpl(
    private val categoryRepository: CategoryRepository,
) : DeleteAllCategoryUseCase {
    override suspend fun invoke() {
        categoryRepository.onDeleteAll()
    }
}