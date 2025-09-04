package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsFirstEntryAppUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository

internal class IsFirstEntryAppUseCaseImpl(
    private val userDataStoreRepository: UserDataStoreRepository,
) : IsFirstEntryAppUseCase {

    override suspend fun invoke(): Boolean {
        val isFirstEntry = userDataStoreRepository.isFirstEnterApp()
        userDataStoreRepository.enabledFirstEnterApp()
        return isFirstEntry
    }
}