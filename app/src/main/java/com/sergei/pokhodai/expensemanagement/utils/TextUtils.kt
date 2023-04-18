package com.sergei.pokhodai.expensemanagement.utils

import android.widget.TextView
import com.sergei.pokhodai.expensemanagement.utils.enums.Language
import com.sergei.pokhodai.expensemanagement.R

fun TextView.getTextDate(date: LocalDateFormatter, today: LocalDateFormatter, language: Language) {
    this.text = when {
        date.dd_MM_yyyy(language) == today.dd_MM_yyyy(language) -> {
            this.rootView.context.getString(R.string.today)
        }
        date.dd_MM_yyyy(language) == today.update { minusDays(1) }.dd_MM_yyyy(language) -> {
            this.rootView.context.getString(R.string.yesterday)
        }
        else -> {
            date.dd_MMMM_yyyy(language)
        }
    }
}

fun CharSequence?.takeIfNotEmpty() = takeIf { !it.isNullOrEmpty() }?.toString()