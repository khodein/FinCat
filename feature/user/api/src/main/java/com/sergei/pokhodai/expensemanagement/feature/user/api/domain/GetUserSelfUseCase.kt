package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel

interface GetUserSelfUseCase {
    suspend operator fun invoke(): UserSelfModel
}