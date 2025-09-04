package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_8_0_24
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.model.CategoryDefaultType
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem

internal class CategoryDialogMapper(
    private val categoryTypeMapper: CategoryTypeMapperImpl,
    private val resManager: ResManager
) {
    fun getTitle() = resManager.getString(R.string.category_dialog_title)

    fun getItems(
        budgetType: BudgetType,
        list: List<CategoryModel>,
        onClick: ((state: CategoryKindItem.State) -> Unit)
    ): List<CategoryKindItem.State> {
        return list.map { model ->
            val categoryDefaultType = CategoryDefaultType.entries.find { it.name == model.name && it.budgetType == budgetType }
            val nameValue: String = categoryDefaultType?.nameResId?.let(resManager::getString) ?: model.name
            CategoryKindItem.State(
                provideId = model.id.toString(),
                container = CategoryKindItem.Container(
                    width = ViewDimension.MatchParent,
                    height = ViewDimension.Dp(100)
                ),
                icon = model.type?.let(categoryTypeMapper::getImageResId)?.let(ImageValue::Res),
                color = ColorValue.Companion.parseColor(model.colorName),
                data = model,
                name = nameValue,
                onClick = onClick
            )
        }
    }

    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "category_dialog_add_id",
            value = resManager.getString(R.string.category_dialog_add_new),
            fill = ButtonItem.Fill.Outline(),
            width = ViewDimension.WrapContent,
            container = ButtonItem.Container(
                paddings = P_0_8_0_24
            ),
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(12),
            onClick = onClick
        )
    }
}