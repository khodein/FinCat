package com.sergei.pokhodai.expensemanagement.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdFlowUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val userRouter: UserRouter,
    localeManager: LocaleManager,
) : ViewModel() {

    val languageFlow = localeManager.getLanguageFlow()

    init {
        onCheckUserId()
    }

    private fun onCheckUserId() {
        viewModelScope.launch {
            getUserIdFlowUseCase.invoke().collect { userId ->
                if (userId == null) {
                    userRouter.goToUser()
                }
            }
        }
    }
}