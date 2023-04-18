package com.sergei.pokhodai.expensemanagement.ui.language

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
): ViewModel() {

    private val language = languageRepository.getLanguage()

    private val _languageFlow = MutableStateFlow(language)
    val languageFlow = _languageFlow.asStateFlow()

    val validateFlow = _languageFlow.map {
        language != languageFlow.value
    }

    fun onChangeLanguage(language: Language) {
        _languageFlow.value = language
    }

    fun onClickLanguage() {
        languageRepository.setLanguage(languageFlow.value)
    }
}