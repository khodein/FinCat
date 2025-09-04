package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

interface IsUserDataStoreEmptyUseCase {
    suspend operator fun invoke(): Boolean
}