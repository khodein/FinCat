package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R

internal class UserAvatarArtMapperImpl : UserAvatarArtMapper {

    override fun getUserArtValue(model: UserAvatarModel): Int {
        return when(model) {
            UserAvatarModel.FUN -> R.drawable.art_fun_cat
            UserAvatarModel.CUTE -> R.drawable.art_cute_cat
            UserAvatarModel.GREY -> R.drawable.art_grey_cat
            UserAvatarModel.PINK -> R.drawable.art_pink_cat
            UserAvatarModel.HAT -> R.drawable.art_hat_cat
            UserAvatarModel.GREEN -> R.drawable.art_green_cat
            UserAvatarModel.RELAX -> R.drawable.art_relax_cat
            UserAvatarModel.GLASSES -> R.drawable.art_glasses_cat
            UserAvatarModel.ORANGE -> R.drawable.art_orange_cat
            UserAvatarModel.YELLOW -> R.drawable.art_yellow_cat
        }
    }
}