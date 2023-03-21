package com.android.pokhodai.expensemanagement.ui.pin_code

import androidx.lifecycle.ViewModel
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModel @Inject constructor(
    val userDataSource: UserDataSource
): ViewModel() {

    val isInstallMode = userDataSource.pinCode.isEmpty()

    private val _title = MutableStateFlow(
        if (isInstallMode) R.string.pin_code_title else R.string.pin_code_set
    )
    val title = _title.asStateFlow()

    private val _digitState = Channel<DigitState>()
    val digitState = _digitState.receiveAsFlow()

    private val _authState = MutableStateFlow(
        if (isInstallMode) PassCodeState.InstallState
        else PassCodeState.EmptyState
    )
    val authState = _authState.asStateFlow()

    var code = ""
        private set

    private var acceptCode = userDataSource.pinCode


    fun onClickNumberButton(digit: Char) {
        if (code.length == CODE_LENGTH) return

        code += digit

        updateCode(true)
    }

    fun onClickBackspaceButton() {
        if (code.length == CODE_LENGTH) return

        code = code.dropLast(1)

        updateCode(false)
    }

    fun onErrorPassCode() {
        code = ""
        _authState.value = PassCodeState.EmptyState
    }

    private fun updateCode(isAdded: Boolean) {
        if (code.length < CODE_LENGTH) {
            _authState.value = PassCodeState.InputState(code)
            _digitState.trySend(
                if (isAdded) DigitState.AddDigit(code.length - 1)
                else DigitState.RemoveDigit(code.length)
            )
            return
        }

        _authState.value = when (userDataSource.pinCode) {
            "" -> {
                if (acceptCode.isEmpty()) {
                    acceptCode = code
                    code = ""
                    _title.value = R.string.pin_code_repeat
                    PassCodeState.EmptyState
                } else if (acceptCode == code) {
                    userDataSource.pinCode = code
                    PassCodeState.SuccessState
                } else {
                    PassCodeState.InstallErrorState
                }
            }
            code -> { //Если мы пытаемся войти
                PassCodeState.SuccessState
            }
            else -> {
                PassCodeState.ErrorState
            }
        }
    }


    sealed class PassCodeState {
        object SuccessState : PassCodeState()
        object InstallState : PassCodeState()
        object InstallErrorState : PassCodeState()
        object ErrorState : PassCodeState()
        object EmptyState : PassCodeState()
        data class InputState(val code: String) : PassCodeState()
    }

    sealed class DigitState {
        data class AddDigit(val index: Int) : DigitState()
        data class RemoveDigit(val index: Int) : DigitState()
    }

    companion object {
        const val CODE_LENGTH = 4
        const val MAX_ERROR = 5
        const val ZERO_ATTEMPTS = 0
    }
}