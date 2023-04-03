package com.android.pokhodai.expensemanagement.repositories

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.utils.enums.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageRepository @Inject constructor(
    private val userDataSource: UserDataSource,
) {
    fun getLanguage() = userDataSource.language ?: Language.EU

    fun setLanguage(language: Language? = getLanguage()) {
        userDataSource.language = language
        setLocale()
    }

    private fun setLocale() {
        val appLocale: LocaleListCompat =
            LocaleListCompat.forLanguageTags(userDataSource.language?.locale ?: Language.EU.locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}