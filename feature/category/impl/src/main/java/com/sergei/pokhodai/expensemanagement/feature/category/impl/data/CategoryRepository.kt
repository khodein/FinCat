package com.sergei.pokhodai.expensemanagement.feature.category.impl.data

import com.sergei.pokhodai.expensemanagement.database.api.dao.CategoryDao
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.mapper.CategoryEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserDataIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CategoryRepository(
    private val categoryDao: CategoryDao,
    private val getUserDataIdUseCase: GetUserDataIdUseCase,
    private val categoryEntityMapper: CategoryEntityMapper,
) {
    suspend fun setCategories(vararg models: CategoryModel) {
        withContext(Dispatchers.IO) {
            val entities = models.map { model ->
                categoryEntityMapper.mapModelToEntity(
                    userId = getUserDataIdUseCase.invoke(),
                    model = model,
                )
            }
            categoryDao.insertAll(*entities.toTypedArray())
        }
    }

    suspend fun getCategoryListByBudgetType(
        budgetType: String
    ): List<CategoryModel> {
        return withContext(Dispatchers.IO) {
            categoryDao.getAllByBudgetType(
                userId = getUserDataIdUseCase.invoke(),
                budgetType = budgetType,
            ).map(categoryEntityMapper::mapEntityToModel)
        }
    }

    suspend fun updateCategory(model: CategoryModel) {
        return withContext(Dispatchers.IO) {
            categoryDao.update(
                categoryEntityMapper.mapModelToEntity(
                    userId = getUserDataIdUseCase.invoke(),
                    model = model
                )
            )
        }
    }

    suspend fun getCategoryById(id: Long): CategoryModel? {
        return withContext(Dispatchers.IO) {
            categoryDao.getById(
                id = id,
                userId = getUserDataIdUseCase.invoke(),
            )?.let(categoryEntityMapper::mapEntityToModel)
        }
    }

    suspend fun onDeleteAll() {
        withContext(Dispatchers.IO) {
            categoryDao.deleteAll()
        }
    }

    suspend fun setDeleteById(id: Long) {
        return withContext(Dispatchers.IO) {
            categoryDao.deleteById(
                id = id,
                userId = getUserDataIdUseCase.invoke()
            )
        }
    }
}