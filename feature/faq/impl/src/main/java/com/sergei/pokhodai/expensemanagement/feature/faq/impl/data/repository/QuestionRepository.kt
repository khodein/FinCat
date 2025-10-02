package com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository

import com.sergei.pokhodai.expensemanagement.core.configprovider.ConfigProvider
import com.sergei.pokhodai.expensemanagement.core.network.api.QuestionApiService
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.QuestionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class QuestionRepository(
    private val questionApiService: QuestionApiService,
    private val configProvider: ConfigProvider,
) {
    suspend fun sendQuestion(
        model: QuestionModel,
    ) {
        withContext(Dispatchers.IO) {
            val message = buildString {
                model.name?.let {
                    append("$NAME $it")
                    appendLine()
                }

                model.email?.let {
                    append("$EMAIL $it")
                    appendLine()
                }

                model.title?.let {
                    append("$TITLE $it")
                    appendLine()
                }

                model.message.let {
                    append("$MESSAGE $it")
                    appendLine()
                }

                append(getSupportInfo())
            }
            questionApiService.sendMessage(message)
        }
    }

    private fun getSupportInfo(): String {
        return "\n${BUILD_INFO}${configProvider.getVersionName()}" +
                "\n${ASSEMBLY} ${configProvider.getVersionCode()}" +
                "\n${DEVICE_MODEL} ${configProvider.getBrand()}" +
                "\n${ANDROID_VERSION} ${configProvider.getBuild()}"
    }

    private companion object {
        const val BUILD_INFO = "BuildInfo: v"
        const val ASSEMBLY = "Assembly:"
        const val DEVICE_MODEL = "DeviceModel:"
        const val ANDROID_VERSION = "Android Version:"
        const val NAME = "Name:"
        const val TITLE = "Title:"
        const val EMAIL = "Email:"
        const val MESSAGE = "Message:"
    }
}