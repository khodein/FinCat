package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.model.CategoryDefaultType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel

internal class CategoryNameMapperImpl(
    private val resManager: ResManager
) : CategoryNameMapper {
    override fun getName(model: CategoryModel?): String {
        val categoryDefaultType = CategoryDefaultType.entries.find { it.name == model?.name }
        val name =
            categoryDefaultType?.nameResId?.let(resManager::getString) ?: model?.name.orEmpty()
        return name
    }

    override fun getName(name: String?): String {
        val categoryDefaultType = CategoryDefaultType.entries.find { it.name == name }
        val name = categoryDefaultType?.nameResId?.let(resManager::getString) ?: name.orEmpty()
        return name
    }
}