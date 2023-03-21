package com.android.pokhodai.expensemanagement

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataSource: UserDataSource
) : ViewModel() {

    private val _navigateFlow = Channel<MainResult>()
    val navigateFlow = _navigateFlow.receiveAsFlow()

    init {
        onPrepareProject()
    }

    fun onPrepareProject() {
        viewModelScope.launch {
            _navigateFlow.send(
                if (userDataSource.user != null) {
                    MainResult.PassCodeResult
                } else {
                    MainResult.UserEmptyResult
                }
            )
        }
    }

    fun onEnterApp(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            _navigateFlow.send(MainResult.EnterTheApplicationResult)
        }
    }

    fun onChangeUser(
        user: User,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            userDataSource.user = user
            onPrepareProject()
        }
    }
}

sealed class MainResult {
    object UserEmptyResult : MainResult()
    object PassCodeResult : MainResult()
    object EnterTheApplicationResult : MainResult()
}