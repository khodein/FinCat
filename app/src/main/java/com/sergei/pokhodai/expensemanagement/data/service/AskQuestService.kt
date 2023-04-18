package com.sergei.pokhodai.expensemanagement.data.service

import com.sergei.pokhodai.expensemanagement.data.models.AskQuestRequest
import com.sergei.pokhodai.expensemanagement.data.settings.ApiSettings
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AskQuestService {

    @POST(ApiSettings.SEND_MESSAGE)
    suspend fun sendMessage(
        @Body body: AskQuestRequest
    ): Response<Unit>
}