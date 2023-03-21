package com.android.pokhodai.expensemanagement.data.service

import com.android.pokhodai.expensemanagement.data.models.AskQuestRequest
import com.android.pokhodai.expensemanagement.data.settings.ApiSettings
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AskQuestService {

    @POST(ApiSettings.SEND_MESSAGE)
    suspend fun sendMessage(
        @Body body: AskQuestRequest
    ): Response<Unit>
}