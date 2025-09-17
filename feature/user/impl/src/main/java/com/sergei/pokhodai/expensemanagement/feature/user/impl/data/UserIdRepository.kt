package com.sergei.pokhodai.expensemanagement.feature.user.impl.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store.DataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class UserIdRepository(
    private val dataStore: DataStore<Preferences>,
) {
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

    fun getUserIdFlow(): Flow<Long?> = userIdFlow

    suspend fun getUserId(): Long? {
        return userIdFlow.firstOrNull()
    }

    suspend fun setUserId(userId: Long?) {
        dataStore.edit { preferences ->
            preferences[DataStoreKey.USER_ID_APP] = userId ?: 0L
        }
    }
}