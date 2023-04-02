package com.android.pokhodai.expensemanagement.utils.enums

import com.android.pokhodai.expensemanagement.R
import java.util.Locale

enum class Language(val locale: String, val nameResIs: Int) {
    EU("en-EN", R.string.english),
    RU("ru-RU", R.string.russian)
}