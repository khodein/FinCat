package com.sergei.pokhodai.expensemanagement.feature.user.api.domain

interface IsFirstEntryAppUseCase {
    suspend operator fun invoke(): Boolean
}