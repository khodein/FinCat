package com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CbrDailyResponse(
    @SerialName("Date") val date: String?,
    @SerialName("PreviousDate") val previousDate: String?,
    @SerialName("Valute") val valute: Map<String, CbrDailyValuteResponse>?
)