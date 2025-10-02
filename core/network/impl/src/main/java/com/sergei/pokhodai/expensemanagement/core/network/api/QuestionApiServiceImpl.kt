package com.sergei.pokhodai.expensemanagement.core.network.api

import com.sergei.pokhodai.expensemanagement.core.network.request.question.QuestionMessageRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import com.sergei.pokhodai.expensemanagement.core.network.impl.BuildConfig

internal class QuestionApiServiceImpl(
    private val httpClient: HttpClient,
) : QuestionApiService {

    override suspend fun sendMessage(message: String) {
        return httpClient.post(
            urlString = "${DOMAIN}/bot${BuildConfig.TG_TOKEN}/${SEND_MESSAGE_METHOD}",
            block = {
                setBody(
                    QuestionMessageRequest(
                        chatId = BuildConfig.TG_CHAT,
                        message = message,
                    )
                )
            }
        ).body()
    }

    private companion object {
        private const val DOMAIN = "https://api.telegram.org"
        private const val SEND_MESSAGE_METHOD = "sendMessage"
    }
}