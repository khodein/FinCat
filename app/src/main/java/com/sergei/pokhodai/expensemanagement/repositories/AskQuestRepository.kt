package com.sergei.pokhodai.expensemanagement.repositories

import com.sergei.pokhodai.expensemanagement.base.ui.repositories.BaseApiRepository
import com.sergei.pokhodai.expensemanagement.data.models.AskQuestRequest
import com.sergei.pokhodai.expensemanagement.data.service.AskQuestService
import com.sergei.pokhodai.expensemanagement.utils.ManagerUtils
import javax.inject.Inject

class AskQuestRepository @Inject constructor(
    private val askQuestService: AskQuestService,
    manager: ManagerUtils
): BaseApiRepository(manager) {

    fun sendMessage(
        chatId: String,
        text: String,
    ) = toResultFlow {
        askQuestService.sendMessage(
            AskQuestRequest(chatId, text)
        )
    }
}