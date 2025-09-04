package com.sergei.pokhodai.expensemanagement.core.support.impl

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import java.util.Locale
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class LocaleManagerImpl(
    private val context: Context,
) : LocaleManager {

    private val languageFlow = MutableStateFlow<LocaleLanguageModel>(LocaleLanguageModel.EN)
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun getLanguage(): LocaleLanguageModel {
        val prefs = prefs.getString(LANGUAGE_KEY, LocaleLanguageModel.EN.name)
            ?: LocaleLanguageModel.EN.name
        return LocaleLanguageModel.valueOf(prefs)
    }

    override fun getLanguageFlow(): StateFlow<LocaleLanguageModel> {
        return languageFlow.asStateFlow()
    }

    override fun updateLanguage(language: LocaleLanguageModel) {
        val correctLanguage = if (isFirstEntry()) {
            language
        } else {
            persistEnabledFirstEntry()
            if (Locale.getDefault().language == LocaleLanguageModel.RU.language) {
                LocaleLanguageModel.RU
            } else {
                LocaleLanguageModel.EN
            }
        }
        persistLanguage(correctLanguage)
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(getLanguage().language)
        AppCompatDelegate.setApplicationLocales(appLocale)
        languageFlow.value = correctLanguage
    }

    private fun persistLanguage(language: LocaleLanguageModel) {
        prefs.edit(commit = true) { putString(LANGUAGE_KEY, language.name) }
    }

    private fun isFirstEntry(): Boolean {
        return prefs.getBoolean(FIRST_ENTRY_KEY, false)
    }

    private fun persistEnabledFirstEntry() {
        prefs.edit(commit = true) { putBoolean(FIRST_ENTRY_KEY, true) }
    }

    private companion object {
        const val LANGUAGE_KEY = "language_key"
        const val FIRST_ENTRY_KEY = "first_entry_language_key"
    }
}