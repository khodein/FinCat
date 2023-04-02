package com.android.pokhodai.expensemanagement

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import com.android.pokhodai.expensemanagement.repositories.LanguageRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

}