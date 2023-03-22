package com.android.pokhodai.expensemanagement.base.ui.repositories

import com.android.pokhodai.expensemanagement.utils.ApiResult
import com.android.pokhodai.expensemanagement.utils.ManagerUtils
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseApiRepository(private val managerUtils: ManagerUtils) {

    inline fun <T> toResultFlow(crossinline call: suspend () -> Response<T>?): Flow<ApiResult<T>> {
        return flow {
            emit(ApiResult.Loading())

            call()?.let { call ->
                try {
                    if (call.isSuccessful) {
                        call.body()?.let { emit(ApiResult.Success(it)) }
                    } else {
                        call.errorBody()?.let { responseBody ->
                            val error = responseBody.string()
                            responseBody.close()
                            emit(ApiResult.Error(error))
                        }
                    }
                } catch (e: Exception) {
                    emit(createError(e))
                }
            }

        }.flowOn(Dispatchers.IO)
    }

    fun createError(e: Exception): ApiResult.Error {
        val error = when (e) {
            is JsonSyntaxException -> RequestError.JSON
            is SocketTimeoutException, is ConnectException -> RequestError.CONNECT
            is CancellationException -> RequestError.COROUTINE_CANCEL
            is UnknownHostException -> RequestError.UNKNOWN_HOST
            else -> RequestError.NONE
        }

        return ApiResult.Error(
            if (error != RequestError.NONE) error.errorMessage else e.localizedMessage,
        )
    }

    private enum class RequestError(val errorMessage: String) {
        UNKNOWN_HOST("Unknown host!"),
        CONNECT("Не удалось подключиться к серверу"),
        JSON("Не удалось декодировать данные"),
        COROUTINE_CANCEL(""),
        NONE("Неизвестная ошибка")
    }
}