package com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper

import com.sergei.pokhodai.expensemanagement.database.api.entity.UserSelfEntity
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel

internal class UserEntityMapper {

    fun mapEntityToModel(entity: UserSelfEntity): UserSelfModel {
        return UserSelfModel(
            userId = entity.primaryId,
            name = entity.name,
            currency = UserCurrencyModel.valueOf(entity.currency),
            email = entity.email
        )
    }

    fun mapModelToEntity(model: UserSelfModel): UserSelfEntity {
        return UserSelfEntity(
            primaryId = model.userId,
            currency = model.currency.name,
            name = model.name,
            email = model.email
        )
    }
}