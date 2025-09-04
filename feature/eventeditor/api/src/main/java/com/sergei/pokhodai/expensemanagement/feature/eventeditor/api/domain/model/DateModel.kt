package com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter

data class DateModel(
    val value: LocalDateFormatter
)