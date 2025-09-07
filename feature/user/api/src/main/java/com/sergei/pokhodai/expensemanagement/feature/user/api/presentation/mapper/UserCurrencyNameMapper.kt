package com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper

import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel

interface UserCurrencyNameMapper {
    fun getNameWithSymbol(model: UserCurrencyModel): String
    fun getNameText(currency: UserCurrencyModel): String
}