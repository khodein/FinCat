package com.android.pokhodai.expensemanagement.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.repositories.ExpenseRepository
import com.android.pokhodai.expensemanagement.repositories.WalletRepository
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.ui.settings.adapter.SettingAdapter
import com.android.pokhodai.expensemanagement.utils.enums.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val walletRepository: WalletRepository,
    private val userDataSource: UserDataSource,
) : ViewModel() {

    private val _userFlow = MutableStateFlow(userDataSource.user)
    val userFlow = _userFlow.asStateFlow()

    private val _logoutFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val logoutFlow = _logoutFlow.asSharedFlow()

    val settingsFlow = MutableStateFlow(
        Settings.values()
            .mapIndexed { index, settings ->
                SettingAdapter.Setting(
                    setting = settings,
                    isVisibleIcon = Settings.values().size - 1 != index
                )
            })


    fun onLogout(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            expenseRepository.deleteAll()
            walletRepository.deleteAll()
            userDataSource.logout()
            _logoutFlow.emit(Unit)
        }
    }
}