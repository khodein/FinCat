package com.sergei.pokhodai.expensemanagement.core.support.api.manager

import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocalModel
import kotlinx.coroutines.flow.StateFlow

interface LocaleManager {
    fun getLanguage(): LocalModel
    fun getLanguageFlow(): StateFlow<LocalModel>
    fun updateLanguage(language: LocalModel)
}