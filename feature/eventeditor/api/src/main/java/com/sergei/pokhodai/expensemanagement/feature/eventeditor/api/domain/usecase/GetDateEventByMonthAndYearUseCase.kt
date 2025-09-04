package com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel

interface GetDateEventByMonthAndYearUseCase {
    suspend fun invoke(
        date: LocalDateFormatter
    ): Map<DateModel, List<EventModel>>
}