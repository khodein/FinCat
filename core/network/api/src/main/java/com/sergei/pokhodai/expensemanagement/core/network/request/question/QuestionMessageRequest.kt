package com.sergei.pokhodai.expensemanagement.core.network.request.question

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionMessageRequest(
    @SerialName("chat_id") val chatId: String,
    @SerialName("text") val message: String,
)