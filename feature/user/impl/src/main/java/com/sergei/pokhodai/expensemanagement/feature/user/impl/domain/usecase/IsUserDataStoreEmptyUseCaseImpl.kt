package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsUserDataStoreEmptyUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository

internal class IsUserDataStoreEmptyUseCaseImpl(
    private val userDataStoreRepository: UserDataStoreRepository,
) : IsUserDataStoreEmptyUseCase {
    override suspend fun invoke(): Boolean {
        return userDataStoreRepository.getUserId() == null
    }
}