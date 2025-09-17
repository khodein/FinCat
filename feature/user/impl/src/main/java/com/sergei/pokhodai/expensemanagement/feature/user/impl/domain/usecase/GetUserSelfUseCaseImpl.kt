package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository

internal class GetUserSelfUseCaseImpl(
    private val getUserDataIdUseCase: GetUserIdUseCase,
    private val userRepository: UserRepository,
): GetUserSelfUseCase {
    override suspend fun invoke(): UserSelfModel {
        val userId = getUserDataIdUseCase.invoke()
        return userRepository.getUserById(userId)
    }
}