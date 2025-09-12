package com.sergei.pokhodai.expensemanagement.core.network.api

import com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily.CbrDailyResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

internal class CbrDailyApiServiceImpl(
    private val httpClient: HttpClient,
    private val json: Json,
) : CbrDailyApiService {

    override suspend fun getDailyResponse(): CbrDailyResponse {
        val response = httpClient.get("${DOMAIN}${DAILY_PATH}")
        return json.decodeFromString(response.bodyAsText())
    }

    companion object {
        private const val DOMAIN = "https://www.cbr-xml-daily.ru/"
        private const val DAILY_PATH = "daily_json.js"
    }
}