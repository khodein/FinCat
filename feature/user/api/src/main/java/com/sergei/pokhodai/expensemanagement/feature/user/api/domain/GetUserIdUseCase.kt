package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

interface GetUserIdUseCase {
    suspend operator fun invoke(): Int?
}