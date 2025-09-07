package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

interface GetUserDataIdUseCase {
    suspend operator fun invoke(): Long
}