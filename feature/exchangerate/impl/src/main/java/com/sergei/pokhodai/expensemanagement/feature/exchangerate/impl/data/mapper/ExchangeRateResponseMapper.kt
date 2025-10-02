package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data.mapper

import com.sergei.pokhodai.expensemanagement.core.base.utils.getEntry
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily.CbrDailyResponse
import com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily.CbrDailyValuteResponse
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.domain.model.CbrDailyModel
import java.math.BigDecimal

internal class ExchangeRateResponseMapper {

    fun mapResponseToModel(
        response: CbrDailyResponse
    ): CbrDailyModel {
        return CbrDailyModel(
            date = response.date?.let(LocalDateFormatter::parseExchangeFormat),
            previousDate = response.previousDate?.let(LocalDateFormatter::parseExchangeFormat),
            valuteModelList = mapValuteResponseToModelList(response.valute)
        )
    }

    private fun mapValuteResponseToModelList(
        response: Map<String, CbrDailyValuteResponse>?
    ): List<CbrDailyModel.ValuteModel> {
        if (response == null) {
            return emptyList()
        }

        return getCbrDailyValuteResponseDetailList(response)
    }

    private fun getCbrDailyValuteResponseDetailList(
        response: Map<String, CbrDailyValuteResponse>?
    ): List<CbrDailyModel.ValuteModel> {
        return buildList {
            response?.forEach {
                mapValuteResponseToModel(it.value)
                    ?.let(::add)
            }
        }
    }

    private fun mapValuteResponseToModel(
        response: CbrDailyValuteResponse?
    ): CbrDailyModel.ValuteModel? {
        if (response == null) {
            return null
        }

        val charCodeResponse = response.charCode
        if (charCodeResponse == null) {
            return null
        }

        val charCodeEnum = CbrDailyModel.CharCode.entries.getEntry(charCodeResponse)
        if (charCodeEnum == null) {
            return null
        }

        return CbrDailyModel.ValuteModel(
            id = response.id.orEmpty(),
            numCode = response.numCode.orEmpty(),
            charCode = charCodeEnum,
            nominal = response.nominal ?: 0,
            name = response.name.orEmpty(),
            value = response.value?.toBigDecimal() ?: BigDecimal.ZERO,
            previousValue = response.previousValue?.toBigDecimal() ?: BigDecimal.ZERO
        )
    }
}