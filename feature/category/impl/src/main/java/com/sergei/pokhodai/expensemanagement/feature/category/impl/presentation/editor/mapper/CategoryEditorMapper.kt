package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.router.support.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.router.support.color.ColorRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.state.CategoryEditorErrorState
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.category_editor.CategoryEditorItem
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryKindMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryNameMapperImpl
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.color_picker.ColorPickerItem
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class CategoryEditorMapper(
    private val categoryKindMapper: CategoryKindMapper,
    private val categoryNameMapperImpl: CategoryNameMapperImpl,
    private val resManager: ResManager,
) {
    fun getGlobalError(): String {
        return resManager.getString(R.string.category_editor_global_error)
    }

    fun getSaveErrorMessage(): String {
        return resManager.getString(R.string.category_editor_save_error)
    }

    fun getDeleteSuccessMessage(): String {
        return resManager.getString(R.string.category_editor_delete_success)
    }

    fun getToolbarState(
        isEdit: Boolean,
        onClickDelete: () -> Unit,
        onClickBack: () -> Unit,
    ): ToolbarItem.State {
        val resId = if (isEdit) {
            R.string.category_editor_title_add
        } else {
            R.string.category_editor_title_edit
        }
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = resManager.getString(resId)
            ),
            isDelete = isEdit,
            onClickDelete = onClickDelete,
            onClickNavigation = onClickBack
        )
    }

    fun getCategoryMessageSuccess(
        isEdit: Boolean
    ): String {
        val resId = if (isEdit) {
            R.string.category_editor_save_add_success
        } else {
            R.string.category_editor_save_edit_success
        }
        return resManager.getString(resId)
    }

    fun getButtonState(
        isLoading: Boolean,
        isEdit: Boolean,
        onClick: ((state: ButtonItem.State) -> Unit)? = null
    ): ButtonItem.State? {
        val resId = if (isEdit) {
            R.string.category_editor_title_edit
        } else {
            R.string.category_editor_title_add
        }
        return ButtonItem.State(
            provideId = "category_editor_button_item",
            width = ViewDimension.MatchParent,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
            isLoading = isLoading,
            container = ButtonItem.Container(
                paddings = P_16_4_16_16,
                backgroundColor = ColorValue.Res(baseR.color.background)
            ),
            fill = ButtonItem.Fill.Filled,
            value = resManager.getString(resId),
            onClick = onClick
        )
    }

    fun getItems(
        categoryModel: CategoryModel,
        itemListProvider: Provider
    ): List<RecyclerState> {
        return listOfNotNull(
            getCategoryEditorItemState(
                model = categoryModel,
                onAfterTextChange = itemListProvider::onChangeName,
                onClickLeading = itemListProvider::onClickIcon
            ),
            getColorPickerItemState(
                model = categoryModel,
                onClick = itemListProvider::onClickColorPicker
            )
        )
    }

    fun getColorRouterModel(
        onClickColor: (hex: String) -> Unit
    ): ColorRouterModel {
        return ColorRouterModel(
            cancelText = resManager.getString(R.string.category_editor_color_picker_confirm),
            confirmText = resManager.getString(R.string.category_editor_color_picker_cancel),
            onClickColor = onClickColor
        )
    }

    fun getAlertQuastionDeleteModel(
        onClickConfirm: () -> Unit,
        onClickCancel: () -> Unit
    ): AlertRouterModel {
        return AlertRouterModel(
            text = resManager.getString(R.string.category_editor_delete_category),
            positiveBtnText = resManager.getString(R.string.category_editor_confirm),
            negativeBtnText = resManager.getString(R.string.category_editor_cancel),
            listenerPositive = onClickConfirm,
            listenerNegative = onClickCancel
        )
    }

    private fun getCategoryEditorItemState(
        model: CategoryModel,
        onAfterTextChange: ((value: String) -> Unit)? = null,
        onClickLeading: (() -> Unit)? = null
    ): CategoryEditorItem.State {
        val name = categoryNameMapperImpl.getName(model)
        val kindItemState = categoryKindMapper.mapCategoryKindItemState(model)
        val textFieldItemState = TextFieldItem.State(
            provideId = "category_editor_field_item_id",
            value = name,
            hint = resManager.getString(R.string.category_editor_name_hint),
            inputType = TextFieldItem.FieldInputType.TEXT,
            onAfterTextChanger = onAfterTextChange
        )

        return CategoryEditorItem.State(
            provideId = "category_editor_item_id",
            textFieldItemState = textFieldItemState,
            paddings = P_16_0_16_16,
            onClickLeading = onClickLeading,
            kindItemState = kindItemState,
        )
    }

    fun getCategoryEditorErrorState(
        name: String,
        type: CategoryType?
    ): CategoryEditorErrorState? {
        val resId = when {
            name.trim().isEmpty() -> R.string.category_editor_name_error
            type == null -> R.string.category_editor_icon_error
            else -> null
        }

        return resId?.let {
            CategoryEditorErrorState(
                message = resManager.getString(it)
            )
        }
    }

    private fun getColorPickerItemState(
        model: CategoryModel,
        onClick: (() -> Unit)? = null
    ): ColorPickerItem.State? {
        return model.type?.let {
            ColorPickerItem.State(
                provideId = "category_editor_color_picker_item_id",
                color = ColorValue.parseColor(model.colorName),
                name = model.colorName,
                container = ColorPickerItem.Container(
                    paddings = P_16_0_16_16
                ),
                onClick = onClick,
            )
        }
    }

    interface Provider {
        fun onClickColorPicker()
        fun onChangeName(value: String)
        fun onClickIcon()
    }
}