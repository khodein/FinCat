package com.android.pokhodai.expensemanagement.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

//ApiResult Flow
enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult<out T>(val status: ApiStatus, private val data: T? = null, val message: String? = null) {

    data class Success<out R>(val data: R) : ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = data,
    )

    data class Error(val exception: String) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        message = exception
    )

    class Loading<out R> : ApiResult<R>(
        status = ApiStatus.LOADING,
    )
}

@Suppress("FunctionName")
fun <R> MutableUIStateFlow(
    sharedFlow: MutableSharedFlow<UIState<R>> = MutableSharedFlow(replay = 1)
) = MutableStateFlowSimulator(sharedFlow)

fun <R> StateFlowSimulator<UIState<R>>.successValue() = (value as? UIState.UISuccess)?.data

sealed class UIState<T> {
    class UINone<T> : UIState<T>()
    data class UILoading<T>(val progress: Float = 0f) : UIState<T>()
    data class UIPreSuccess<T>(val data: T) : UIState<T>()
    data class UISuccess<T>(val data: T) : UIState<T>()
    data class UIError<T>(val errorMessage: String) : UIState<T>()
}

suspend inline fun <T, R> MutableSharedFlow<UIState<R>>.emitRequest(
    request: Flow<ApiResult<T>>,
    isOnlySuccess: Boolean = false,
    crossinline onLoading: () -> Unit = { },
    crossinline onError: (String) -> String = { message -> message },
    crossinline onSuccess: suspend (T) -> R,
) {

    request.collect {
        val state = when (it) {
            is ApiResult.Loading -> {
                if (isOnlySuccess) return@collect
                onLoading.invoke()
                UIState.UILoading()
            }
            is ApiResult.Success -> {
                val data = onSuccess.invoke(it.data)
                emit(UIState.UIPreSuccess(data))
                UIState.UISuccess(data)
            }
            is ApiResult.Error -> {
                UIState.UIError(onError.invoke(it.message ?: ""))
            }
        }

        if (it is ApiResult.Loading) return@collect emit(state)

        if (!isOnlySuccess) emit(UIState.UINone())
        emit(state)

        if (!isOnlySuccess && it is ApiResult.Error) {
            emit(UIState.UINone())
        }
    }
}

fun <T> MutableStateFlowSimulator<UIState<T>>.asUIStateFlow(): StateFlowSimulator<UIState<T>> = this

class MutableStateFlowSimulator<R>(
    flow: MutableSharedFlow<R>
) : StateFlowSimulator<R>(flow), MutableSharedFlow<R> {
    override var value: R? = null
        private set

    fun asStateFlowSimulator(): StateFlowSimulator<R> = this

    override suspend fun emit(value: R) {
        subscriptionCount.first { it > 0 }
        flow.emit(value).also { this.value = value }
    }

    override fun tryEmit(value: R) = flow.tryEmit(value).also { this.value = value }

    override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = flow.resetReplayCache()
}

abstract class StateFlowSimulator<R>(
    protected val flow: MutableSharedFlow<R>
) : SharedFlow<R> by flow {
    abstract val value: R?
}


