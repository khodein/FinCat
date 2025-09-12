package com.sergei.pokhodai.expensemanagement.core.network.response.cbrdaily

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CbrDailyValuteResponse(
    @SerialName("ID") val id: String?,
    @SerialName("NumCode") val numCode: String?,
    @SerialName("CharCode") val charCode: String?,
    @SerialName("Nominal") val nominal: Int?,
    @SerialName("Name") val name: String?,
    @SerialName("Value") val value: String?,
    @SerialName("Previous") val previousValue: String?
)