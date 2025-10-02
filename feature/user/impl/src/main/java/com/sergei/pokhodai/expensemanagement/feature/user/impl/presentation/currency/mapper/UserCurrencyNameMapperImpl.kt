package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper

import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R

internal class UserCurrencyNameMapperImpl(
    private val resManager: ResManager,
) : UserCurrencyNameMapper {

    override fun getNameWithSymbol(
        model: UserCurrencyModel
    ): String {
        return "${getNameText(model)} · ${model.symbol}"
    }

    override fun getNameText(currency: UserCurrencyModel): String {
        val resId = when (currency) {
            UserCurrencyModel.USD -> R.string.currency_usd_title
            UserCurrencyModel.RUB -> R.string.currency_rub_title
            UserCurrencyModel.BYN -> R.string.currency_byn_title
            UserCurrencyModel.KZT -> R.string.currency_kzt_title
            UserCurrencyModel.CNY -> R.string.currency_cny_title
            UserCurrencyModel.EUR -> R.string.currency_eur_title
        }
        return resManager.getString(resId)
    }
}