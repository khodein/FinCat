package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data

import com.sergei.pokhodai.expensemanagement.core.network.api.CbrDailyApiService
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.domain.model.CbrDailyModel
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data.mapper.ExchangeRateResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ExchangeRateRepository(
    private val responseMapper: ExchangeRateResponseMapper,
    private val cbrDailyApiService: CbrDailyApiService,
) {
    private var valuteModelList: List<CbrDailyModel.ValuteModel>? = null

    private var startTime: Long = 0L

    suspend fun getDaily(): CbrDailyModel {
        return withContext(Dispatchers.IO) {
            responseMapper.mapResponseToModel(cbrDailyApiService.getDailyResponse())
        }
    }

    suspend fun getDailyValuteList(
        isUpdateCache: Boolean = false
    ): List<CbrDailyModel.ValuteModel> {
        if (isUpdateCache || isMoreThanMinutesSince()) {
            valuteModelList = null
        }

        return valuteModelList ?: getDaily().valuteModelList.also {
            valuteModelList = it
        }
    }

    private fun isMoreThanMinutesSince(): Boolean {
        val currentTime = System.currentTimeMillis()
        val fiveMinutesInMillis = IS_UPDATE_CACHE_MINUTE * 60 * 1000
        return (currentTime - startTime) > fiveMinutesInMillis
    }

    private companion object {
        const val IS_UPDATE_CACHE_MINUTE = 5
    }
}