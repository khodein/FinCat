package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserDataIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository

internal class GetUserIdUseCaseImpl(
    private val userDataStoreRepository: UserDataStoreRepository
) : GetUserDataIdUseCase {
    override suspend fun invoke(): Long {
        return userDataStoreRepository.getUserId() ?: throw Throwable()
    }
}