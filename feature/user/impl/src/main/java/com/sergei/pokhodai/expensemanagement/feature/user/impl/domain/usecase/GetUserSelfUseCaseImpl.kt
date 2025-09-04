package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository

internal class GetUserSelfUseCaseImpl(
    private val userDataStoreRepository: UserDataStoreRepository,
) : GetUserSelfUseCase {
    override suspend fun invoke(): UserSelfModel {
        return userDataStoreRepository.getUserDataStore()
    }
}