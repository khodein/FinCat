package com.sergei.pokhodai.expensemanagement.core.support.impl.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.LocaleManager
import java.util.Locale
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocalModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class LocaleManagerImpl(
    private val context: Context,
) : LocaleManager {

    private val languageFlow = MutableStateFlow<LocalModel>(LocalModel.EN)
    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun getLanguage(): LocalModel {
        val prefs = prefs.getString(LANGUAGE_KEY, LocalModel.EN.name)
            ?: LocalModel.EN.name
        return LocalModel.valueOf(prefs)
    }

    override fun getLanguageFlow(): StateFlow<LocalModel> {
        return languageFlow.asStateFlow()
    }

    override fun updateLanguage(language: LocalModel) {
        val correctLanguage = if (isFirstEntry()) {
            language
        } else {
            persistEnabledFirstEntry()
            if (Locale.getDefault().language == LocalModel.RU.language) {
                LocalModel.RU
            } else {
                LocalModel.EN
            }
        }
        persistLanguage(correctLanguage)
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(getLanguage().language)
        AppCompatDelegate.setApplicationLocales(appLocale)
        languageFlow.value = correctLanguage
    }

    private fun persistLanguage(language: LocalModel) {
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