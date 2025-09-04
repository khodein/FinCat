package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.router.support.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.AmountModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.R
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.state.EventEditorErrorState
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.ui.event_category.EventEditorCategoryItem
import com.sergei.pokhodai.expensemanagement.uikit.dropdown.DropDownItem
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.select.SelectItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import java.math.BigDecimal

internal class EventEditorMapper(
    private val categoryTypeMapper: CategoryTypeMapper,
    private val categoryNameMapper: CategoryNameMapper,
    private val resManager: ResManager,
) {
    fun getItems(
        eventModel: EventModel,
        itemListProvider: Provider,
    ): List<RecyclerState> {
        return listOf(
            getBudgetItemState(
                budgetType = eventModel.budgetType,
                onClick = itemListProvider::onClickBudgetType
            ),
            getCategoryItemState(
                model = eventModel.categoryModel,
                onClickCategoryName = itemListProvider::onClickCategory,
                onClickCategoryLeading = itemListProvider::onClickCategory
            ),
            getAmountItemState(
                amountModel = eventModel.amountModel,
                onAfterTextChange = itemListProvider::onChangeAmount
            ),
            getDateItemState(
                createDate = eventModel.dateModel,
                onClickDate = itemListProvider::onClickDate
            ),
            getDescriptionItemState(
                description = eventModel.description,
                onAfterTextChange = itemListProvider::onChangeDescription
            )
        )
    }

    fun getToolbarState(
        isEdit: Boolean,
        onClickBack: () -> Unit,
        onClickDelete: (() -> Unit),
    ): ToolbarItem.State {
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = getToolbarTitleValue(isEdit)
            ),
            isDelete = isEdit,
            onClickNavigation = onClickBack,
            onClickDelete = onClickDelete
        )
    }

    private fun getToolbarTitleValue(
        isEdit: Boolean
    ): String {
        val valueResId = if (isEdit) {
            R.string.event_editor_title_edit
        } else {
            R.string.event_editor_title_add_new
        }
        return resManager.getString(valueResId)
    }

    fun getErrorState(
        categoryModel: CategoryModel?,
        amountModel: AmountModel,
    ): EventEditorErrorState? {
        val resId = when {
            categoryModel == null -> R.string.event_editor_category_error
            amountModel == AmountModel.getDefault() -> R.string.event_editor_amount_error
            else -> null
        }

        return resId?.let {
            EventEditorErrorState(
                message = resManager.getString(resId)
            )
        }
    }

    fun getButtonState(
        isLoading: Boolean,
        budgetType: BudgetType,
        isEdit: Boolean,
        onClick: ((state: ButtonItem.State) -> Unit)? = null
    ): ButtonItem.State? {
        return ButtonItem.State(
            provideId = "editor_button_item",
            width = ViewDimension.MatchParent,
            isLoading = isLoading,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
            container = ButtonItem.Container(
                paddings = P_16_4_16_16,
                backgroundColor = ColorValue.Res(baseR.color.background)
            ),
            fill = ButtonItem.Fill.Filled,
            value = getButtonValue(
                budgetType = budgetType,
                isEdit = isEdit,
            ),
            onClick = onClick
        )
    }

    private fun getButtonValue(
        budgetType: BudgetType,
        isEdit: Boolean,
    ): String {
        val valueRes = when (budgetType) {
            BudgetType.INCOME -> if (isEdit) {
                R.string.event_editor_btn_edit_income
            } else {
                R.string.event_editor_btn_income
            }

            BudgetType.EXPENSE -> if (isEdit) {
                R.string.event_editor_btn_edit_expense
            } else {
                R.string.event_editor_btn_expense
            }
        }
        return resManager.getString(valueRes)
    }

    private fun getBudgetItemState(
        budgetType: BudgetType,
        onClick: (item: DropDownItem.Item) -> Unit
    ): DropDownItem.State {
        val items = BudgetType.entries.map {
            DropDownItem.Item(
                value = getBudgetTextValue(it),
                data = it
            )
        }
        return DropDownItem.State(
            provideId = "editor_budget_item_id",
            container = DropDownItem.Container(
                paddings = P_16_0_16_16
            ),
            value = getBudgetTextValue(budgetType),
            items = items,
            onClickItem = onClick,
        )
    }

    private fun getCategoryItemState(
        model: CategoryModel?,
        onClickCategoryName: (() -> Unit)? = null,
        onClickCategoryLeading: (() -> Unit)? = null
    ): EventEditorCategoryItem.State {
        val name = model?.let(categoryNameMapper::getName).orEmpty()
        val kindItemState = mapCategoryKindItemState(model)

        return EventEditorCategoryItem.State(
            provideId = "editor_category_item_id",
            selectItemState = SelectItem.State(
                provideId = "editor_category_select_item_id",
                value = name,
                hind = resManager.getString(R.string.event_editor_category_name),
                onClick = onClickCategoryName
            ),
            paddings = P_16_0_16_16,
            onClick = onClickCategoryLeading,
            kindItemState = kindItemState,
        )
    }

    private fun mapCategoryKindItemState(
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

    private fun getAmountItemState(
        amountModel: AmountModel,
        onAfterTextChange: ((value: String, money: BigDecimal) -> Unit)? = null,
    ): TextFieldItem.State {
        val initialValue = if (amountModel.value.compareTo(BigDecimal.ZERO) == 0) {
            ""
        } else {
            amountModel.format
        }
        return TextFieldItem.State(
            provideId = "editor_amount_item_id",
            value = initialValue,
            isMoney = true,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16,
            ),
            hint = resManager.getString(R.string.event_editor_amount_hint),
            inputType = TextFieldItem.FieldInputType.DECIMAL,
            onMoneyTextChanger = onAfterTextChange
        )
    }

    private fun getDateItemState(
        createDate: DateModel,
        onClickDate: (() -> Unit)? = null,
    ): SelectItem.State {
        return SelectItem.State(
            provideId = "editor_create_date_item_id",
            value = createDate.value.dd_MM_yyyy(),
            container = SelectItem.Container(
                paddings = P_16_0_16_16,
            ),
            hind = "",
            onClick = onClickDate
        )
    }

    private fun getDescriptionItemState(
        description: String,
        onAfterTextChange: ((value: String) -> Unit)? = null,
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "editor_description_item_id",
            value = description,
            imeOption = TextFieldItem.FieldImeOption.ENTER,
            inputType = TextFieldItem.FieldInputType.MULTILINE,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16
            ),
            line = TextFieldItem.Line(
                minLine = 3,
                maxLines = 3,
                lines = 3,
                isSingleLine = false
            ),
            onAfterTextChanger = onAfterTextChange,
            hint = resManager.getString(R.string.event_editor_description_hint),
        )
    }

    private fun getBudgetTextValue(budgetType: BudgetType): String {
        val valueResId = when (budgetType) {
            BudgetType.EXPENSE -> R.string.event_editor_expense
            BudgetType.INCOME -> R.string.event_editor_income
        }
        return resManager.getString(valueResId)
    }

    fun getSaveMessageSuccess(isEdit: Boolean): String {
        val resId = if (isEdit) {
            R.string.event_editor_save_edit_success
        } else {
            R.string.event_editor_save_add_success
        }
        return resManager.getString(resId)
    }

    fun getDeleteMessageSuccess(): String {
        return resManager.getString(R.string.event_editor_delete_success)
    }

    fun getGlobalError(): String {
        return resManager.getString(R.string.event_editor_global_error)
    }

    fun getSaveErrorMessage(): String {
        return resManager.getString(R.string.event_editor_save_error)
    }

    fun getDeleteQuestionAlert(
        onClickConfirm: () -> Unit,
        onClickCancel: () -> Unit,
    ): AlertRouterModel {
        return AlertRouterModel(
            text = resManager.getString(R.string.event_editor_delete_question),
            positiveBtnText = resManager.getString(R.string.event_editor_question_confirm),
            negativeBtnText = resManager.getString(R.string.event_editor_question_cancel),
            listenerPositive = onClickConfirm,
            listenerNegative = onClickCancel
        )
    }

    interface Provider {
        fun onClickDate()
        fun onClickCategory()
        fun onChangeAmount(value: String, money: BigDecimal)
        fun onChangeDescription(value: String)
        fun onClickBudgetType(item: DropDownItem.Item)
    }
}