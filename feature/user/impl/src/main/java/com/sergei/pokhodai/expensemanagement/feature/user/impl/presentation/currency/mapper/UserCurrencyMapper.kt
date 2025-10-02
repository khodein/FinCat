package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem

internal class UserCurrencyMapper(
    private val resManager: ResManager,
    private val userCurrencyNameMapper: UserCurrencyNameMapper
) {
    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "currency_item_id",
            width = ViewDimension.MatchParent,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
            container = ButtonItem.Container(
                paddings = P_16_4_16_16,
                backgroundColor = ColorValue.Res(baseR.color.background)
            ),
            fill = ButtonItem.Fill.Filled,
            value = resManager.getString(R.string.user_language_button),
            onClick = onClick
        )
    }

    fun getTagItems(
        currency: UserCurrencyModel,
        list: List<UserCurrencyModel>,
        onClickTag: (state: TagItem.State) -> Unit
    ): List<TagItem.State> {
        return list.map { model ->
            getTagItem(
                currency = currency,
                model = model,
                onClickTag = onClickTag
            )
        }
    }

    private fun getTagItem(
        currency: UserCurrencyModel,
        model: UserCurrencyModel,
        onClickTag: (state: TagItem.State) -> Unit
    ): TagItem.State {
        return TagItem.State(
            provideId = model.name,
            isActive = model == currency,
            value = userCurrencyNameMapper.getNameWithSymbol(model),
            data = model,
            onClick = onClickTag
        )
    }

    fun getTitle(): String {
        return resManager.getString(R.string.user_currency_title)
    }
}