package com.sergei.pokhodai.expensemanagement.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsFirstEntryAppUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsUserDataStoreEmptyUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val isUserDataStoreEmptyUseCase: IsUserDataStoreEmptyUseCase,
    private val localeManager: LocaleManager,
    private val userRouter: UserRouter,
) : ViewModel() {

    val languageFlow = localeManager.getLanguageFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            if (isUserDataStoreEmptyUseCase.invoke()) {
                userRouter.goToUser()
            }
        }
    }
}