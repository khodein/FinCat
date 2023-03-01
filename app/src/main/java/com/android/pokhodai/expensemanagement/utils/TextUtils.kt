package com.android.pokhodai.expensemanagement.utils

import android.widget.TextView
import com.android.pokhodai.expensemanagement.R

fun TextView.getTextDate(date: LocalDateFormatter, today: LocalDateFormatter) {
    this.text = when {
        date.dd_MM_yyyy() == today.dd_MM_yyyy() -> {
            this.rootView.context.getString(R.string.today)
        }
        date.dd_MM_yyyy() == today.update { minusDays(1) }.dd_MM_yyyy() -> {
            this.rootView.context.getString(R.string.yesterday)
        }
        else -> {
            date.dd_MMMM_yyyy()
        }
    }
}