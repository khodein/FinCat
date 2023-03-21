package com.android.pokhodai.expensemanagement.repositories

import com.android.pokhodai.expensemanagement.base.ui.repositories.BaseApiRepository
import com.android.pokhodai.expensemanagement.data.models.AskQuestRequest
import com.android.pokhodai.expensemanagement.data.service.AskQuestService
import javax.inject.Inject

class AskQuestRepository @Inject constructor(
    private val askQuestService: AskQuestService
): BaseApiRepository() {

    fun sendMessage(
        chatId: Long,
        text: String,
    ) = toResultFlow {
        askQuestService.sendMessage(
            AskQuestRequest(chatId, text)
        )
    }
}