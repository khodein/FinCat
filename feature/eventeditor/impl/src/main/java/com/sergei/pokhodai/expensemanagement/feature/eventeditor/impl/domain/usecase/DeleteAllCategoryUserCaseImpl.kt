package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.domain.usecase

import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.DeleteAllCategoryUserCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.EventRepository

internal class DeleteAllCategoryUserCaseImpl(
    private val eventRepository: EventRepository,
): DeleteAllCategoryUserCase {
    override suspend fun invoke() {
        eventRepository.onDeleteAll()
    }
}