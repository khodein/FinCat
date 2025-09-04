package com.sergei.pokhodai.expensemanagement.feature.category.impl.data

import com.sergei.pokhodai.expensemanagement.database.api.dao.CategoryDao
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.mapper.CategoryEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val categoryEntityMapper: CategoryEntityMapper,
) {
    suspend fun setCategories(vararg models: CategoryModel) {
        val userId = getUserIdUseCase.invoke() ?: throw Throwable()
        withContext(Dispatchers.IO) {
            val entities = models.map { model ->
                categoryEntityMapper.mapModelToEntity(
                    userId = userId,
                    model = model,
                )
            }
            categoryDao.insertAll(*entities.toTypedArray())
        }
    }

    suspend fun getCategoryListByBudgetType(
        budgetType: String
    ): List<CategoryModel> {
        val userId = getUserIdUseCase.invoke() ?: throw Throwable()
        return withContext(Dispatchers.IO) {
            categoryDao.getAllByBudgetType(
                userId = userId,
                budgetType = budgetType,
            ).map(categoryEntityMapper::mapEntityToModel)
        }
    }

    suspend fun updateCategory(model: CategoryModel) {
        val userId = getUserIdUseCase.invoke() ?: throw Throwable()
        return withContext(Dispatchers.IO) {
            categoryDao.update(
                categoryEntityMapper.mapModelToEntity(
                    userId = userId,
                    model = model
                )
            )
        }
    }

    suspend fun getCategoryById(id: Int): CategoryModel? {
        val userId = getUserIdUseCase.invoke() ?: throw Throwable()
        return withContext(Dispatchers.IO) {
            categoryDao.getById(
                id = id,
                userId = userId,
            )?.let(categoryEntityMapper::mapEntityToModel)
        }
    }

    suspend fun setDeleteById(id: Int) {
        val userId = getUserIdUseCase.invoke() ?: throw Throwable()
        return withContext(Dispatchers.IO) {
            categoryDao.deleteById(
                id = id,
                userId = userId
            )
        }
    }
}