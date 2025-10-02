package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserCurrencyUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository

internal class GetUserCurrencyUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserCurrencyUseCase {

    override suspend fun invoke(
        userId: Long,
    ): UserCurrencyModel? {
        return userRepository.getUserById(userId).currency
    }
}