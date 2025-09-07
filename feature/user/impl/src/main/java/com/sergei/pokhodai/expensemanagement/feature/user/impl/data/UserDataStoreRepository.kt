package com.sergei.pokhodai.expensemanagement.feature.user.impl.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store.DataStoreKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class UserDataStoreRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private val isFirstEnterAppFlow: Flow<Boolean> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DataStoreKey.FIRST_ENTER_APP] ?: false
    }
    private val userIdFlow: Flow<Long?> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        val id = preferences[DataStoreKey.USER_ID_APP]
        if (id == 0L) {
            return@map null
        } else {
            id
        }
    }

    suspend fun isFirstEnterApp(): Boolean {
        return withContext(Dispatchers.IO) {
            isFirstEnterAppFlow.firstOrNull() ?: false
        }
    }

    suspend fun enabledFirstEnterApp() {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences -> preferences[DataStoreKey.FIRST_ENTER_APP] = true }
        }
    }

    suspend fun getUserId(): Long? {
        return withContext(Dispatchers.IO) {
            return@withContext userIdFlow.firstOrNull()
        }
    }

    suspend fun setUserData(userId: Long?) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[DataStoreKey.USER_ID_APP] = userId ?: 0L
            }
        }
    }
}