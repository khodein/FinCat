package com.sergei.pokhodai.expensemanagement.core.network.api

interface QuestionApiService {
    suspend fun sendMessage(message: String)
}