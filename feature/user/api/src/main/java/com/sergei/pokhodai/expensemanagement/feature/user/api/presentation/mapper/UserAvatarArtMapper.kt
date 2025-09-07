package com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper

import androidx.annotation.DrawableRes
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel

interface UserAvatarArtMapper {

    @DrawableRes
    fun getUserArtValue(model: UserAvatarModel): Int
}