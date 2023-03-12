package com.android.pokhodai.expensemanagement.repositories

import android.content.Context
import android.content.res.Configuration
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.utils.enums.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class LanguageRepository(
    private val userDataSource: UserDataSource,
    private val context: Context,
) {

    fun setAppLocale() {
        setConfiguration(
            Locale(userDataSource.language?.locale
                ?: Language.EU.locale)
        )
    }

    fun getLanguage() = userDataSource.language ?: Language.EU

    fun setLanguage(language: Language) {
        userDataSource.language = language
        setConfiguration(Locale(language.locale))
    }

    private fun setConfiguration(locale: Locale) {
        Locale.setDefault(locale)
        val config = context.resources.configuration.apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }
        context.createConfigurationContext(config)
    }
}