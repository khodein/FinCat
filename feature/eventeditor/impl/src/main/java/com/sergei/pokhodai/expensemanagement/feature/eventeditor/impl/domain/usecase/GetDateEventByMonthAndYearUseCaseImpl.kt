package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetDateEventByMonthAndYearUseCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.EventRepository

internal class GetDateEventByMonthAndYearUseCaseImpl(
    private val eventRepository: EventRepository
) : GetDateEventByMonthAndYearUseCase {

    override suspend fun invoke(
        date: LocalDateFormatter
    ): Map<DateModel, List<EventModel>> {
        return eventRepository.getDateEventByMonthAndYear(date)
    }
}