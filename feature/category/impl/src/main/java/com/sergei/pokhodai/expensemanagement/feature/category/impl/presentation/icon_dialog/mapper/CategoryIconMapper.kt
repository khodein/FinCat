package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.mapper

import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryIconModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryTypeMapperImpl
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class CategoryIconMapper(
    private val resManager: ResManager,
    private val categoryTypeMapper: CategoryTypeMapperImpl,
) {
    fun getTitle(): String {
        return resManager.getString(R.string.category_icon_title)
    }

    fun getItems(
        onClick: ((state: CategoryKindItem.State) -> Unit)? = null
    ): List<RecyclerState> {
        return CategoryType.entries.map { categoryType ->
            val colorName = categoryTypeMapper.getColorStr(categoryType)
            CategoryKindItem.State(
                provideId = categoryType.name,
                icon = categoryTypeMapper.getImageResId(categoryType).let(ImageValue::Res),
                container = CategoryKindItem.Container(
                    width = ViewDimension.MatchParent,
                    height = ViewDimension.Dp(80),
                ),
                data = CategoryIconModel(
                    type = categoryType,
                    colorName = colorName,
                ),
                color = ColorValue.parseColor(colorName),
                onClick = onClick
            )
        }
    }
}