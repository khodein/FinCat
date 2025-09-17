package com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdFlowUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserIdRepository
import kotlinx.coroutines.flow.Flow

internal class GetUserIdFlowUseCaseImpl(
    private val userDataStoreRepository: UserIdRepository
): GetUserIdFlowUseCase {
    override fun invoke(): Flow<Long?> {
        return userDataStoreRepository.getUserIdFlow()
    }
}