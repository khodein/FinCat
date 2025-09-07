package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object DataStoreKey {
    const val DS_NAME = "settings_fin_cat"
    private const val FIRST_ENTER_APP_NAME = "first_enter_app"
    private const val USER_ID = "user_id"

    val FIRST_ENTER_APP = booleanPreferencesKey(FIRST_ENTER_APP_NAME)
    val USER_ID_APP = longPreferencesKey(USER_ID)
}