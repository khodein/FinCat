package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list.UserAvatarListItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem

internal class UserAvatarMapper(
    private val userAvatarArtMapper: UserAvatarArtMapper,
    private val resManager: ResManager,
) {
    fun getItems(
        avatar: UserAvatarModel?,
        list: List<UserAvatarModel>,
        onClick: (state: UserAvatarListItem.State) -> Unit
    ): List<RecyclerState> {
        return list.map { model ->
            getAvatarListItem(
                avatar = avatar,
                model = model,
                onClick = onClick,
            )
        }
    }

    fun getTitleText(): String {
        return resManager.getString(R.string.user_avatar_title)
    }

    fun getButtonItemState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "avatar_item_id",
            width = ViewDimension.MatchParent,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
            container = ButtonItem.Container(
                paddings = P_16_4_16_16,
                backgroundColor = ColorValue.Res(baseR.color.background)
            ),
            fill = ButtonItem.Fill.Filled,
            value = resManager.getString(R.string.user_avatar_button),
            onClick = onClick
        )
    }

    private fun getAvatarListItem(
        avatar: UserAvatarModel?,
        model: UserAvatarModel,
        onClick: (state: UserAvatarListItem.State) -> Unit
    ): UserAvatarListItem.State {
        return UserAvatarListItem.State(
            provideId = model.name,
            isChecked = avatar == model,
            art = userAvatarArtMapper.getUserArtValue(model).let(ImageValue::Coil),
            onClick = onClick,
            data = model
        )
    }
}