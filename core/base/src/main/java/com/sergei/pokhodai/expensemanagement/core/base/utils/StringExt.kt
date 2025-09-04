package com.sergei.pokhodai.expensemanagement.core.base.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

fun String.getFormatCurrency(locale: Locale = Locale.getDefault()): Pair<String, BigDecimal> {
    fun getZero() = Pair("0", BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))

    if (this.isEmpty()) return getZero()

    return try {
        val bigDecimal = BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN)

        val isInteger = bigDecimal.stripTrailingZeros().scale() <= 0
        val formatted = NumberFormat.getNumberInstance(locale).apply {
            isGroupingUsed = true
            minimumFractionDigits = if (isInteger) 0 else 2
            maximumFractionDigits = 2
        }.format(bigDecimal)

        Pair(formatted, bigDecimal)
    } catch (e: Exception) {
        getZero()
    }
}