package com.sergei.pokhodai.expensemanagement.feature.settings.impl.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.R

enum class SettingModel(
    @StringRes val nameResId: Int,
    @DrawableRes val leadingIconRes: Int,
) {
    MANAGER_CATEGORY(
        nameResId = R.string.settings_manage,
        leadingIconRes = R.drawable.ic_manager_category,
    ),

    VALUTE(
        nameResId = R.string.settings_exchange,
        leadingIconRes = R.drawable.ic_attach_money
    ),

    ASKED_QUESTION(
        nameResId = R.string.settings_questions,
        leadingIconRes = R.drawable.ic_quiz
    ),

    LANGUAGE(
        nameResId = R.string.settings_language,
        leadingIconRes = R.drawable.ic_language
    ),

    LOGOUT(
        nameResId = R.string.settings_logout,
        leadingIconRes = R.drawable.ic_logout,
    )
}