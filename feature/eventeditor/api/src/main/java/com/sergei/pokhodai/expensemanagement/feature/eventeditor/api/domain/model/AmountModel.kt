package com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model

import java.math.BigDecimal

data class AmountModel(
    val value: BigDecimal,
    val format: String
) {
    companion object {
        const val FORMAT_ZERO = ""
        fun getDefault(): AmountModel {
            return AmountModel(
                value = BigDecimal.ZERO,
                format = FORMAT_ZERO
            )
        }
    }
}