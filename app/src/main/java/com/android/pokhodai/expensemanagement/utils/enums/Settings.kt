package com.android.pokhodai.expensemanagement.utils.enums

import com.android.pokhodai.expensemanagement.R

enum class Settings(val iconResId: Int, val textResId: Int) {
    MANAGER(R.drawable.ic_manager_category, R.string.settings_manage),
    CHOOSE_LANGUAGE(R.drawable.ic_language, R.string.settings_language),
    ASKED_QUESTIONS(R.drawable.ic_quiz, R.string.settings_questions),
    LOGOUT(R.drawable.ic_quiz, R.string.settings_logout)
}