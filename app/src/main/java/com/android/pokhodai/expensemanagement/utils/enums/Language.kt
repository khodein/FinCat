package com.android.pokhodai.expensemanagement.utils.enums

import com.android.pokhodai.expensemanagement.R
import java.util.Locale

enum class Language(val locale: String, val nameResIs: Int, val calendarLocale: String) {
    EU("en-EN", R.string.english, "en"),
    RU("ru-RU", R.string.russian, "ru")
}