package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data

import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.mapper.EventEntityMapper
import com.sergei.pokhodai.expensemanagement.database.api.dao.EventDao
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class EventRepository(
    private val eventEntityMapper: EventEntityMapper,
    private val userIdUseCase: GetUserIdUseCase,
    private val eventDao: EventDao,
) {
    suspend fun setEvents(vararg models: EventModel) {
        withContext(Dispatchers.IO) {
            val entities = models.map { model ->
                eventEntityMapper.mapModelToEntity(
                    userId = userIdUseCase.invoke(),
                    model = model,
                )
            }
            eventDao.insertAll(*entities.toTypedArray())
        }
    }

    suspend fun updateEvent(model: EventModel) {
        return withContext(Dispatchers.IO) {
            eventDao.update(
                eventEntityMapper.mapModelToEntity(
                    userId = userIdUseCase.invoke(),
                    model = model
                )
            )
        }
    }

    suspend fun getEventById(id: Long): EventModel? {
        return withContext(Dispatchers.IO) {
            eventDao.getById(
                id = id,
                userId = userIdUseCase.invoke(),
            )?.let(eventEntityMapper::mapEntityToModel)
        }
    }

    suspend fun getDateEventByMonthAndYear(
        date: LocalDateFormatter,
    ): Map<DateModel, List<EventModel>> {
        return withContext(Dispatchers.IO) {
            eventDao.getEventByMonthAndYear(
                dateMonth = date.MM(),
                dateYear = date.yyyy(),
                userId = userIdUseCase.invoke(),
            )
                .map(eventEntityMapper::mapEntityToModel)
                .groupBy { it.dateModel }
        }
    }

    suspend fun onDeleteAll() {
        withContext(Dispatchers.IO) {
            eventDao.deleteAllEvents()
        }
    }

    suspend fun getCategoryEventByMonthAndYearAndBudgetType(
        date: LocalDateFormatter,
        budgetType: BudgetType,
    ): Map<CategoryModel, List<EventModel>> {
        return withContext(Dispatchers.IO) {
            eventDao.getEventByMonthAndYearAndBudgetType(
                dateMonth = date.MM(),
                dateYear = date.yyyy(),
                budgetType = budgetType.name,
                userId = userIdUseCase.invoke()
            )
                .map(eventEntityMapper::mapEntityToModel)
                .groupBy { it.categoryModel?.copy(budgetType = budgetType) as CategoryModel }
        }
    }

    suspend fun setDeleteEventById(id: Long) {
        return withContext(Dispatchers.IO) {
            eventDao.deleteById(
                id = id,
                userId = userIdUseCase.invoke()
            )
        }
    }
}