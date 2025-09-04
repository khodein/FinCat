package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object DataStoreKey {
    const val DS_NAME = "settings_fin_cat"
    private const val FIRST_ENTER_APP_NAME = "first_enter_app"
    private const val USER_NAME = "user_name"
    private const val USER_EMAIL = "user_email"
    private const val USER_ID = "user_id"
    private const val USER_CURRENCY = "user_currency"

    val FIRST_ENTER_APP = booleanPreferencesKey(FIRST_ENTER_APP_NAME)

    val USER_NAME_APP = stringPreferencesKey(USER_NAME)
    val USER_EMAIL_APP = stringPreferencesKey(USER_EMAIL)
    val USER_ID_APP = intPreferencesKey(USER_ID)
    val USER_CURRENCY_APP = stringPreferencesKey(USER_CURRENCY)
}