package com.android.pokhodai.expensemanagement

import android.net.Uri
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pokhodai.expensemanagement.source.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    private val _bnvVisible = MutableStateFlow(true)
    val bnvVisible = _bnvVisible.asStateFlow()

    private val _navigateToDeepLink = Channel<TabDeepLink>(Channel.CONFLATED)
    val navigateToDeepLink = _navigateToDeepLink.receiveAsFlow()

    private val _refreshFlow = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val refreshFlow = _refreshFlow.asSharedFlow()

    fun onChangeBnvVisible(isVisible: Boolean) {
        _bnvVisible.value = isVisible
    }

    fun onSwipeRefresh(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            _refreshFlow.emit(Unit)
        }
    }

    fun onClickHardDeepLink(uri: Uri, tabId: Int) {
        _navigateToDeepLink.trySend(TabDeepLink(uri, tabId))
    }

    data class TabDeepLink(val uri: Uri, @IdRes val tabId: Int)
}
