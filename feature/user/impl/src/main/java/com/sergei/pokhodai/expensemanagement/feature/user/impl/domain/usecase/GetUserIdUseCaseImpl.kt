package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserIdRepository

internal class GetUserIdUseCaseImpl(
    private val userDataStoreRepository: UserIdRepository
) : GetUserIdUseCase {
    override suspend fun invoke(): Long {
        return userDataStoreRepository.getUserId() ?: throw Throwable()
    }
}