package com.sergei.pokhodai.expensemanagement.feature.category.impl.domain

import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryDefaultRepository

internal class GetCategoryDefaultListUseCaseImpl(
    private val categoryDefaultRepository: CategoryDefaultRepository,
) : GetCategoryDefaultListUseCase {

    override suspend fun invoke(): List<CategoryModel> {
        return categoryDefaultRepository.getDefaultList()
    }
}