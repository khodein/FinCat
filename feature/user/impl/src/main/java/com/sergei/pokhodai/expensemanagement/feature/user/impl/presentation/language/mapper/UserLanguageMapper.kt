package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem

internal class UserLanguageMapper(
    private val resManager: ResManager
) {
    fun getItems(
        localeLanguageModel: LocaleLanguageModel,
        list: List<LocaleLanguageModel>,
        onClick: (state: UserLanguageItem.State) -> Unit
    ): List<UserLanguageItem.State> {
        return list.mapIndexed { index, model ->
            UserLanguageItem.State(
                provideId = model.name,
                container = UserLanguageItem.Container(
                    padding = if (index == list.lastIndex) {
                        P_16_0_16_16
                    } else {
                        P_16_0_16_8
                    }
                ),
                data = model,
                isChecked = localeLanguageModel == model,
                icon = getIcon(model),
                name = getNameText(model),
                onClick = onClick
            )
        }
    }

    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "language_item_id",
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

    private fun getIcon(model: LocaleLanguageModel): ImageValue {
        val resId = when(model) {
            LocaleLanguageModel.RU -> R.drawable.ic_russian
            LocaleLanguageModel.EN -> R.drawable.ic_english
        }
        return ImageValue.Res(resId)
    }

    fun getNameText(model: LocaleLanguageModel): String {
        val resId = when(model) {
            LocaleLanguageModel.EN -> R.string.user_eu_language
            LocaleLanguageModel.RU -> R.string.user_ru_language
        }
        return resManager.getString(resId)
    }

    fun getTitleText(): String {
        return resManager.getString(R.string.user_language_title)
    }
}