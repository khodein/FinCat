package com.sergei.pokhodai.expensemanagement.core.network.api

import com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily.CbrDailyResponse

interface CbrDailyApiService {
    suspend fun getDailyResponse(): CbrDailyResponse
}