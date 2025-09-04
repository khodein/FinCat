package com.sergei.pokhodai.expensemanagement.core.support.api

import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LocaleManager {
    fun getLanguage(): LocaleLanguageModel
    fun getLanguageFlow(): StateFlow<LocaleLanguageModel>
    fun updateLanguage(language: LocaleLanguageModel)
}