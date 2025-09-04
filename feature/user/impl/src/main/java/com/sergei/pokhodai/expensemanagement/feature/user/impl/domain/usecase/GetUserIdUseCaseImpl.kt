package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository

internal class GetUserIdUseCaseImpl(
    private val userDataStoreRepository: UserDataStoreRepository
) : GetUserIdUseCase {
    override suspend fun invoke(): Int? {
        return userDataStoreRepository.getUserDataStore().userId
    }
}