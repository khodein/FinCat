package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserDataIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelf
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository

internal class GetUserSelfImpl(
    private val getUserDataIdUseCase: GetUserDataIdUseCase,
    private val userRepository: UserRepository,
): GetUserSelf {
    override suspend fun invoke(): UserSelfModel {
        val userId = getUserDataIdUseCase.invoke()
        return userRepository.getUserById(userId)
    }
}