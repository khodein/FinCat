package com.android.pokhodai.expensemanagement

import android.net.Uri
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    private val _bnvVisible = MutableStateFlow(true)
    val bnvVisible = _bnvVisible.asStateFlow()

    private val _navigateToDeepLink = Channel<TabDeepLink>(Channel.CONFLATED)
    val navigateToDeepLink = _navigateToDeepLink.receiveAsFlow()

    fun onChangeBnvVisible(isVisible: Boolean) {
        _bnvVisible.value = isVisible
    }

    fun onClickHardDeepLink(uri: Uri, tabId: Int) {
        _navigateToDeepLink.trySend(TabDeepLink(uri, tabId))
    }

    data class TabDeepLink(val uri: Uri, @IdRes val tabId: Int)
}
