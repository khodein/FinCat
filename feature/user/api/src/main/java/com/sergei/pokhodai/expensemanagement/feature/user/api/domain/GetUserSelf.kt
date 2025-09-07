package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel

interface GetUserSelf {
    suspend operator fun invoke(): UserSelfModel
}