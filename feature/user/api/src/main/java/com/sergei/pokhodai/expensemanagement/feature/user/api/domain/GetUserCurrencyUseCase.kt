package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel

interface GetUserCurrencyUseCase {
    suspend operator fun invoke(
        userId: Long
    ): UserCurrencyModel?
}