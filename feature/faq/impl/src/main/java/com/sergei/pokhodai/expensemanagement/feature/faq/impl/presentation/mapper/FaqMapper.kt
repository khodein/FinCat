package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_0_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_4_8_12
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.R
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.FaqModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui.FaqItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class FaqMapper(
    private val resManager: ResManager
) {
    fun getItems(
        list: List<FaqModel>,
    ): List<RecyclerState> {
        return buildList {
            list.mapIndexed { index, model ->
                add(
                    getFaqItemState(
                        isFirst = index == 0,
                        model = model
                    )
                )
            }
        }
    }

    fun getButtonQuestion(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "button_question_id",
            width = ViewDimension.MatchParent,
            height = ViewDimension.Dp(45),
            onClick = onClick,
            value = resManager.getString(R.string.faq_button_question),
            fill = ButtonItem.Fill.Filled,
            container = ButtonItem.Container(
                paddings = P_8_4_8_12,
                backgroundColor = ColorValue.Res(baseR.color.background)
            )
        )
    }

    private fun getFaqItemState(
        isFirst: Boolean,
        model: FaqModel
    ): FaqItem.State {
        val (title, description) = getTitleAndDescription(model)
        return FaqItem.State(
            provideId = model.name,
            title = title,
            description = description,
            padding = if (isFirst) {
                P_8_8_8_8
            } else {
                P_8_0_8_8
            }
        )
    }

    private fun getTitleAndDescription(
        model: FaqModel,
    ): Pair<String, String> {
        val (titleRes, descriptionRes) = when (model) {
            FaqModel.CREDIT -> R.string.faq_credit_title to R.string.faq_credit_description
            FaqModel.USERS -> R.string.faq_users_title to R.string.faq_users_description
            FaqModel.CATEGORY -> R.string.faq_category_title to R.string.faq_category_description
            FaqModel.ENTER_CURRENCY -> R.string.faq_enter_currency_title to R.string.faq_enter_currency_description
            FaqModel.EXCHANGE_RATE -> R.string.faq_exchange_rate_title to R.string.faq_exchange_rate_description
            FaqModel.LANGUAGE -> R.string.faq_language_title to R.string.faq_language_description
        }

        return resManager.getString(titleRes) to resManager.getString(descriptionRes)
    }

    fun getToolbarItemState(
        onClickBack: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.faq_top_title)
            ),
            onClickNavigation = onClickBack
        )
    }
}