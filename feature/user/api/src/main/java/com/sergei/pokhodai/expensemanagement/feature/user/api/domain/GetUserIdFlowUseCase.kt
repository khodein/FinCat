package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

import kotlinx.coroutines.flow.Flow

interface GetUserIdFlowUseCase {
    operator fun invoke(): Flow<Long?>
}