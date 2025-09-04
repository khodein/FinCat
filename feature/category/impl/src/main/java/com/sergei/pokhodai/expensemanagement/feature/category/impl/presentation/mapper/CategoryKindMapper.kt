package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class CategoryKindMapper(
    private val categoryTypeMapper: CategoryTypeMapper
) {
    fun mapCategoryKindItemState(
        model: CategoryModel?,
        onClickKind: ((state: CategoryKindItem.State) -> Unit)? = null
    ): CategoryKindItem.State? {
        return model?.type?.let {
            CategoryKindItem.State(
                provideId = "category_kind_item_id",

                icon = categoryTypeMapper.getImageResId(it).let(ImageValue::Res),
                color = ColorValue.parseColor(model.colorName),
                onClick = onClickKind
            )
        }
    }

    fun mapCategoryKindState(
        model: CategoryModel?,
    ): CategoryKindItem.State {
        return CategoryKindItem.State(
            provideId = "category_kind_item_id",
            icon = model?.type?.let(categoryTypeMapper::getImageResId)?.let(ImageValue::Res),
            color = ColorValue.parseColor(model?.colorName),
        )
    }
}