package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.model.UserDataModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel

internal class UserDataStoreMapper {

    fun mapModelToDataStore(model: UserSelfModel): UserDataModel {
        return UserDataModel(
            userId = model.userId ?: 0,
            name = model.name,
            email = model.email,
            currency = model.currency.name
        )
    }

    fun mapDataStoreToModel(dataStore: UserDataModel): UserSelfModel {
        return UserSelfModel(
            userId = if (dataStore.userId == 0) {
                null
            } else {
                dataStore.userId
            },
            name = dataStore.name,
            email = dataStore.email,
            currency = if (dataStore.currency.isEmpty()) {
                UserCurrencyModel.USD
            } else {
                UserCurrencyModel.valueOf(dataStore.currency)
            }
        )
    }
 }