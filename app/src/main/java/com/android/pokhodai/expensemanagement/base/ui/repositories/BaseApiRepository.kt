package com.android.pokhodai.expensemanagement.base.ui.repositories

import com.android.pokhodai.expensemanagement.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseApiRepository {

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
                    emit(ApiResult.Error(e.toString()))
                }
            }

        }.flowOn(Dispatchers.IO)
    }
}