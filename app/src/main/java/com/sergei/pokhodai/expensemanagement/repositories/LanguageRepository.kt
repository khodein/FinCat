package com.sergei.pokhodai.expensemanagement.repositories

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepository @Inject constructor(
    private val userDataSource: UserDataSource,
) {
    fun getLanguage() = userDataSource.language ?: Language.EU

    fun setLanguage(language: Language? = getLanguage()) {
        if (!userDataSource.isFirstEntry) {
            userDataSource.language = if (Locale.getDefault().language == "ru") {
                    Language.RU
                } else {
                    Language.EU
                }
        } else {
            userDataSource.language = language
        }
        setLocale()
    }

    private fun setLocale() {
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(userDataSource.language?.locale ?: Language.EU.locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}