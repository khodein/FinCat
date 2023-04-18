package com.sergei.pokhodai.expensemanagement.utils.enums

import com.sergei.pokhodai.expensemanagement.R

enum class Language(val locale: String, val nameResIs: Int, val calendarLocale: String) {
    EU("en-EN", R.string.english, "en"),
    RU("ru-RU", R.string.russian, "ru")
}