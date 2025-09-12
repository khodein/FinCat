package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.mapper

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_24
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.support.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryBudgetTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.mapper.CategoryKindMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager.CategoryManagerItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class CategoryManagerMapper(
    private val categoryKindMapper: CategoryKindMapper,
    private val categoryNameMapper: CategoryNameMapper,
    private val categoryBudgetTypeMapper: CategoryBudgetTypeMapper,
    private val resManager: ResManager,
) {
    fun getToolbarItemState(
        isDelete: Boolean,
        onClickBack: () -> Unit,
        onClickDelete: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            isDelete = isDelete,
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.category_manager_title)
            ),
            onClickDelete = onClickDelete,
            onClickNavigation = onClickBack
        )
    }

    fun getItems(
        list: List<CategoryModel>,
        onClickEdit: (state: CategoryManagerItem.State) -> Unit,
        onClickDelete: (state: CategoryManagerItem.State) -> Unit,
    ): List<RecyclerState> {
        return list.map { model ->
            getCategoryManagerItemState(
                model = model,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit
            )
        }
    }

    private fun getCategoryManagerItemState(
        model: CategoryModel,
        onClickEdit: (state: CategoryManagerItem.State) -> Unit,
        onClickDelete: (state: CategoryManagerItem.State) -> Unit,
    ): CategoryManagerItem.State {
        return CategoryManagerItem.State(
            provideId = model.id.toString(),
            categoryKindItemState = categoryKindMapper.mapCategoryKindState(model),
            name = categoryNameMapper.getName(model),
            budgetName = model.budgetType?.let(categoryBudgetTypeMapper::getBudgetName).orEmpty(),
            data = model,
            onClickEdit = onClickEdit,
            onClickDelete = onClickDelete
        )
    }

    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "category_manager_button_item_id",
            fill = ButtonItem.Fill.Filled,
            radius = ViewDimension.Dp(40),
            height = ViewDimension.Dp(48),
            container = ButtonItem.Container(
                paddings = P_0_0_0_24
            ),
            value = resManager.getString(R.string.category_manager_button_add),
            onClick = onClick
        )
    }

    fun getRequestErrorState(
        onClickReload: () -> Unit
    ): RequestItem.State.Error {
        return RequestItem.State.Error(
            message = resManager.getString(R.string.category_manager_global_error),
            onClickReload = onClickReload,
        )
    }

    fun getRequestEmptyState(): RequestItem.State.Empty {
        return RequestItem.State.Empty(
            message = resManager.getString(R.string.category_manager_empty)
        )
    }

    fun getAlertDeleteCategoryId(
        onClickConfirm: () -> Unit
    ): AlertRouterModel {
        return AlertRouterModel(
            text = resManager.getString(R.string.category_manager_delete_id_question),
            positiveBtnText = resManager.getString(R.string.category_manager_confirm),
            negativeBtnText = resManager.getString(R.string.category_manager_cancel),
            listenerNegative = {},
            listenerPositive = onClickConfirm
        )
    }

    fun getDeleteCategoryIdSuccessMessage(): String {
        return resManager.getString(R.string.category_manager_delete_id_success)
    }
    fun getDeleteCategoryIdErrorMessage(): String {
        return resManager.getString(R.string.category_manager_delete_id_error)
    }

    fun getAlertDeleteAll(
        onClickConfirm: () -> Unit
    ): AlertRouterModel {
        return AlertRouterModel(
            text = resManager.getString(R.string.category_manager_delete_all_question),
            positiveBtnText = resManager.getString(R.string.category_manager_confirm),
            negativeBtnText = resManager.getString(R.string.category_manager_cancel),
            listenerNegative = {},
            listenerPositive = onClickConfirm
        )
    }
    fun getDeleteAllSuccessMessage(): String {
        return resManager.getString(R.string.category_manager_delete_all_success)
    }
}