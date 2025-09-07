package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_16_0_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_16_16_16
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper.UserCurrencyMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.mapper.UserLanguageMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.state.UserErrorState
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.state.UserSettingState
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar.UserAvatarItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.field.TextFieldItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class UserMapper(
    private val resManager: ResManager,
    private val userCurrencyNameMapper: UserCurrencyNameMapper,
    private val userLanguageMapper: UserLanguageMapper,
    private val userAvatarArtMapper: UserAvatarArtMapper,
) {
    fun getToolbarItemState(
        isEmptyDataStoreUser: Boolean,
        onClickBack: () -> Unit,
    ): ToolbarItem.State {
        return ToolbarItem.State(
            navigateIcon = if (isEmptyDataStoreUser) {
                null
            } else {
                ToolbarItem.NavigateIcon()
            },
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.user_title)
            ),
            onClickNavigation = onClickBack
        )
    }

    fun getButtonItemState(
        isEdit: Boolean,
        isLoading: Boolean,
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        val resId = if (isEdit) {
            R.string.user_button_edit
        } else {
            R.string.user_button_add
        }
        return ButtonItem.State(
            provideId = "user_button_item",
            width = ViewDimension.MatchParent,
            isLoading = isLoading,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
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
        userModel: UserSelfModel,
        language: LocaleLanguageModel,
        isEdit: Boolean,
        isEmptyDataStoreUser: Boolean,
        provider: ItemListProvider,
    ): List<RecyclerState> {
        return buildList {
            userModel.avatar?.let {
                add(
                    getUserAvatar(
                        avatar = it,
                        onClickEdit = provider::onClickEditAvatar
                    )
                )
            }
            add(
                getNameTextFieldItemState(
                    value = userModel.name,
                    onChangeName = provider::onChangeName
                )
            )

            add(
                getEmailTextFieldITemState(
                    value = userModel.email,
                    onChangeEmail = provider::onChangeEmail
                )
            )

            add(
                getSettingsTags(
                    currency = userModel.currency,
                    language = language,
                    isEdit = isEdit,
                    isEmptyDataStoreUser = isEmptyDataStoreUser,
                    provider = provider,
                )
            )
        }
    }

    private fun getUserAvatar(
        avatar: UserAvatarModel,
        onClickEdit: () -> Unit
    ): UserAvatarItem.State {
        return UserAvatarItem.State(
            provideId = "user_avatar_item_id",
            container = UserAvatarItem.Container(
                paddings = P_0_16_0_0
            ),
            editText = resManager.getString(R.string.user_avatar_edit),
            art = userAvatarArtMapper.getUserArtValue(avatar).let(ImageValue::Coil),
            onClickEdit = onClickEdit
        )
    }

    private fun getNameTextFieldItemState(
        value: String,
        onChangeName: ((String) -> Unit)
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_name_id",
            value = value,
            container = TextFieldItem.Container(
                paddings = P_16_16_16_16
            ),
            hint = resManager.getString(R.string.user_hint_name),
            onAfterTextChanger = onChangeName,
        )
    }

    private fun getEmailTextFieldITemState(
        value: String,
        onChangeEmail: ((String) -> Unit)
    ): TextFieldItem.State {
        return TextFieldItem.State(
            provideId = "text_field_item_email_id",
            value = value,
            container = TextFieldItem.Container(
                paddings = P_16_0_16_16,
            ),
            hint = resManager.getString(R.string.user_hint_email),
            onAfterTextChanger = onChangeEmail
        )
    }

    private fun getSettingsTags(
        currency: UserCurrencyModel?,
        language: LocaleLanguageModel,
        isEmptyDataStoreUser: Boolean,
        isEdit: Boolean,
        provider: ItemListProvider,
    ): UserTagsContainerItem.State {
        return UserTagsContainerItem.State(
            provideId = "user_tags_container_settings_item",
            tags = getTags(
                currency = currency?.let(userCurrencyNameMapper::getNameWithSymbol),
                language = userLanguageMapper.getNameText(language),
                isEdit = isEdit,
                isEmptyDataStoreUser = isEmptyDataStoreUser,
                onClickTag = provider::onClickTag
            ),
            container = UserTagsContainerItem.Container(
                paddings = P_16_0_16_16,
            ),
        )
    }

    private fun getTags(
        currency: String?,
        language: String,
        isEdit: Boolean,
        isEmptyDataStoreUser: Boolean,
        onClickTag: (TagItem.State) -> Unit,
    ): List<TagItem.State> {
        return buildList {
            currency?.let {
                add(
                    getTagItemState(
                        value = currency,
                        isEdit = isEdit,
                        onClickTag = onClickTag,
                        data = UserSettingState.CURRENCY
                    )
                )
            }

            if (isEmptyDataStoreUser) {
                add(
                    getTagItemState(
                        value = language,
                        isEdit = isEdit,
                        onClickTag = onClickTag,
                        data = UserSettingState.LANGUAGE
                    )
                )
            }
        }
    }

    private fun getTagItemState(
        value: String,
        data: UserSettingState,
        isEdit: Boolean,
        onClickTag: (state: TagItem.State) -> Unit
    ): TagItem.State {
        return TagItem.State(
            provideId = data.name,
            value = value,
            data = data,
            isEnabled = !(data == UserSettingState.CURRENCY && isEdit),
            onClick = onClickTag
        )
    }

    fun getSuccessSaveText() = resManager.getString(R.string.user_save_success)
    fun getGlobalErrorText() = resManager.getString(R.string.user_load_error)
    fun getNameErrorText() = resManager.getString(R.string.user_name_error)
    fun getEmailErrorText() = resManager.getString(R.string.user_email_error)
    fun getExitDataStoreEmptyText() = resManager.getString(R.string.user_empty_data)

    interface ItemListProvider {
        fun onChangeName(name: String)
        fun onChangeEmail(email: String)
        fun onClickTag(state: TagItem.State)
        fun onClickEditAvatar()
    }
}