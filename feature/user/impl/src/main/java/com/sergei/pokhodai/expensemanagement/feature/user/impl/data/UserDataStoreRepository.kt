package com.sergei.pokhodai.expensemanagement.feature.user.impl.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserDataStoreMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.model.UserDataModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store.DataStoreKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class UserDataStoreRepository(
    private val userDataStoreMapper: UserDataStoreMapper,
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

    private val userNameFlow: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DataStoreKey.USER_NAME_APP] ?: ""
    }

    private val userEmailFlow: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DataStoreKey.USER_EMAIL_APP] ?: ""
    }

    private val userIdFlow: Flow<Int> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DataStoreKey.USER_ID_APP] ?: 0
    }

    private val userCurrencyFlow: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DataStoreKey.USER_CURRENCY_APP] ?: ""
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

    private suspend fun getUserData(): UserDataModel {
        return withContext(Dispatchers.IO) {
            val name = userNameFlow.firstOrNull() ?: ""
            val email = userEmailFlow.firstOrNull() ?: ""
            val id = userIdFlow.firstOrNull() ?: 0
            val currency = userCurrencyFlow.firstOrNull() ?: ""
            return@withContext UserDataModel(
                name = name,
                email = email,
                userId = id,
                currency = currency
            )
        }
    }

    private suspend fun setUserData(data: UserDataModel) {
        withContext(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[DataStoreKey.USER_NAME_APP] = data.name
                preferences[DataStoreKey.USER_EMAIL_APP] = data.email
                preferences[DataStoreKey.USER_ID_APP] = data.userId
                preferences[DataStoreKey.USER_CURRENCY_APP] = data.currency
            }
        }
    }

    suspend fun setUserDataStore(model: UserSelfModel) {
        return withContext(Dispatchers.IO) {
            setUserData(userDataStoreMapper.mapModelToDataStore(model))
        }
    }

    suspend fun getUserDataStore(): UserSelfModel {
        return withContext(Dispatchers.IO) {
            userDataStoreMapper.mapDataStoreToModel(getUserData())
        }
    }
}