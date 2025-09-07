package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list.mapper

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_24
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_0_16_0
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_0_8_0
import com.sergei.pokhodai.expensemanagement.core.router.support.alert.AlertRouterModel
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper.UserAvatarMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user.UserListItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class UserListMapper(
    private val resManager: ResManager,
    private val userCurrencyNameMapper: UserCurrencyNameMapper,
    private val userAvatarArtMapper: UserAvatarArtMapper,
) {

    fun getToolbarItemState(
        onClickBack: () -> Unit,
        onClickDelete: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            isDelete = true,
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.user_list_title)
            ),
            onClickDelete = onClickDelete,
            onClickNavigation = onClickBack
        )
    }

    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "user_list_button_item_id",
            fill = ButtonItem.Fill.Filled,
            radius = ViewDimension.Dp(40),
            height = ViewDimension.Dp(48),
            container = ButtonItem.Container(
                paddings = P_0_0_0_24
            ),
            value = resManager.getString(R.string.user_list_button),
            onClick = onClick
        )
    }

    fun getItems(
        userDataStoreId: Long,
        list: List<UserSelfModel>,
        provider: ItemListProvider,
    ): List<UserListItem.State> {
        if (list.isEmpty()) {
            return emptyList()
        }
        return list.mapIndexed { index, model ->
            getUserListItemState(
                index = index,
                isOne = list.size == 1,
                model = model,
                userDataStoreId = userDataStoreId,
                onClick = provider::onClickItem,
                onClickEdit = provider::onClickEdit,
                onClickDelete = provider::onClickDelete
            )
        }
    }

    private fun getUserListItemState(
        index: Int,
        isOne: Boolean,
        userDataStoreId: Long,
        model: UserSelfModel,
        onClickDelete: ((state: UserListItem.State) -> Unit),
        onClickEdit: ((state: UserListItem.State) -> Unit),
        onClick: ((state: UserListItem.State) -> Unit)
    ): UserListItem.State {
        val isChecked = userDataStoreId == model.userId
        return UserListItem.State(
            provideId = index.toString(),
            name = model.name,
            container = UserListItem.Container(
                paddings = P_8_0_8_0
            ),
            email = model.email.ifEmpty { null },
            isChecked = isChecked,
            art = model.avatar
                ?.let(userAvatarArtMapper::getUserArtValue)
                ?.let(ImageValue::Coil),
            data = model,
            onClickDelete = if (isOne || isChecked) {
                null
            } else {
                onClickDelete
            },
            currency = model.currency?.let(userCurrencyNameMapper::getNameWithSymbol),
            onClickEdit = onClickEdit,
            onClick = if (isChecked) {
                null
            } else {
                onClick
            }
        )
    }

    fun getRequestEmpty(): RequestItem.State.Empty {
        return RequestItem.State.Empty(
            message = resManager.getString(R.string.user_list_empty)
        )
    }

    fun getRequestError(
        onReload: () -> Unit
    ): RequestItem.State.Error {
        return RequestItem.State.Error(
            message = resManager.getString(R.string.user_load_error),
            onClickReload = onReload
        )
    }

    fun getRequestLoading(): RequestItem.State.Loading {
        return RequestItem.State.Loading
    }

    fun getSnackDeleteError(): String {
        return resManager.getString(R.string.user_list_delete_error)
    }

    fun getSnackChangeError(): String {
        return resManager.getString(R.string.user_list_change_error)
    }

    fun getAlertDeleteAll(
        onClickConfirm: () -> Unit
    ): AlertRouterModel {
        return AlertRouterModel(
            text = resManager.getString(R.string.user_list_delete_all_text),
            positiveBtnText = resManager.getString(R.string.user_list_delete_all_confirm),
            negativeBtnText = resManager.getString(R.string.user_list_delete_all_no),
            listenerPositive = onClickConfirm,
            listenerNegative = {}
        )
    }

    interface ItemListProvider {
        fun onClickDelete(state: UserListItem.State)
        fun onClickItem(state: UserListItem.State)
        fun onClickEdit(state: UserListItem.State)
    }
}