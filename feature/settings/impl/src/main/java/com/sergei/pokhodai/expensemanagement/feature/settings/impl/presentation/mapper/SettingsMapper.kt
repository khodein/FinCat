package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.mapper

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.R
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.domain.model.SettingModel
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile.SettingProfileItem
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings.SettingItem
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR

internal class SettingsMapper(
    private val resManager: ResManager,
    private val userAvatarArtMapper: UserAvatarArtMapper,
    private val userCurrencyNameMapper: UserCurrencyNameMapper,
) {
    fun getToolbarItemState(): ToolbarItem.State {
        return ToolbarItem.State(
            navigateIcon = null,
            backgroundColor = ColorValue.Res(baseR.color.grey_200),
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.settings_title)
            )
        )
    }

    fun getItems(
        list: List<SettingModel>,
        userSelfModel: UserSelfModel,
        onClickSetting: (state: SettingItem.State) -> Unit,
        onClickProfile: (state: SettingProfileItem.State) -> Unit,
    ): List<RecyclerState> {
        return buildList {
            add(
                SettingProfileItem.State(
                    provideId = "settings_profile_item_id",
                    name = userSelfModel.name,
                    email = userSelfModel.email.ifEmpty { null },
                    data = userSelfModel,
                    currency = userSelfModel.currency?.let(userCurrencyNameMapper::getNameWithSymbol),
                    art = userSelfModel.avatar
                        ?.let(userAvatarArtMapper::getUserArtValue)
                        ?.let(ImageValue::Coil),
                    onClick = onClickProfile
                )
            )
            list.forEachIndexed { index, model ->
                SettingItem.State(
                    provideId = model.name,
                    leadingIcon = ImageValue.Res(getLeadingIcon(model)),
                    trailingIcon = if (index == list.lastIndex) {
                        null
                    } else {
                        ImageValue.Res(R.drawable.ic_arrow_setting)
                    },
                    name = resManager.getString(getName(model)),
                    data = model,
                    onClick = onClickSetting
                ).let(::add)
            }
        }
    }

    @DrawableRes
    private fun getLeadingIcon(model: SettingModel): Int {
        return when(model) {
            SettingModel.MANAGER_CATEGORY -> R.drawable.ic_manager_category
            SettingModel.FAQ -> R.drawable.ic_quiz
            SettingModel.LOGOUT -> R.drawable.ic_logout
            SettingModel.VALUTE -> R.drawable.ic_attach_money
            SettingModel.LANGUAGE -> R.drawable.ic_language
        }
    }

    @StringRes
    private fun getName(model: SettingModel): Int {
        return when(model) {
            SettingModel.MANAGER_CATEGORY -> R.string.settings_manage
            SettingModel.FAQ -> R.string.settings_questions
            SettingModel.LOGOUT -> R.string.settings_logout
            SettingModel.VALUTE -> R.string.settings_exchange
            SettingModel.LANGUAGE -> R.string.settings_language
        }
    }

    fun getRequestError(
        onReload: () -> Unit
    ): RequestItem.State.Error {
        return RequestItem.State.Error(
            message = resManager.getString(R.string.settings_error),
            onClickReload = onReload
        )
    }

    fun getRequestEmpty(): RequestItem.State.Empty {
        return RequestItem.State.Empty(resManager.getString(R.string.settings_empty))
    }
}