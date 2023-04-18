package com.sergei.pokhodai.expensemanagement

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application(), LanguageService {

    @Inject
    lateinit var languageRepository: LanguageRepository

    override fun onCreate() {
        super.onCreate()
        initTheme()
        initLang()
    }

    private fun initLang() {
        languageRepository.setLanguage()
    }

    private fun initTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun getLanguagePreparation(): LanguageRepository = languageRepository
}

interface LanguageService {
    fun getLanguagePreparation(): LanguageRepository
}