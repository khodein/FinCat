package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store

import androidx.datastore.preferences.core.longPreferencesKey

internal object UserDataStoreKey {
    const val DS_NAME = "settings_fin_cat"
    private const val USER_ID = "user_id"
    val USER_ID_APP = longPreferencesKey(USER_ID)
}