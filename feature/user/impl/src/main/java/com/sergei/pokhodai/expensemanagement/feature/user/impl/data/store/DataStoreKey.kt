package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object DataStoreKey {
    const val DS_NAME = "settings_fin_cat"
    private const val USER_ID = "user_id"
    val USER_ID_APP = longPreferencesKey(USER_ID)
}