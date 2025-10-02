package com.sergei.pokhodai.expensemanagement.core.network

import android.util.Log
import com.sergei.pokhodai.expensemanagement.core.network.api.CbrDailyApiService
import com.sergei.pokhodai.expensemanagement.core.network.api.CbrDailyApiServiceImpl
import com.sergei.pokhodai.expensemanagement.core.network.api.QuestionApiService
import com.sergei.pokhodai.expensemanagement.core.network.api.QuestionApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object NetworkModule {

    private const val NETWORK_TIME_OUT = 6_000L

    fun get() = module {
        singleOf(::getJson)
        singleOf(::getClient)

        singleOf(::CbrDailyApiServiceImpl) bind CbrDailyApiService::class
        singleOf(::QuestionApiServiceImpl) bind QuestionApiService::class
    }

    private fun getJson(): Json {
        return Json {
            prettyPrint = true
            isLenient = true
            useAlternativeNames = true
            ignoreUnknownKeys = true
            encodeDefaults = false
        }
    }

    private fun getClient(json: Json): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = NETWORK_TIME_OUT
                connectTimeoutMillis = NETWORK_TIME_OUT
                socketTimeoutMillis = NETWORK_TIME_OUT
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.JavaScript
                )
            }

            defaultRequest {
                contentType(ContentType.Application.JavaScript)
                accept(ContentType.Application.JavaScript)

                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }

}