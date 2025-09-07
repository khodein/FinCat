package com.sergei.pokhodai.expensemanagement.feature.user.impl.data

import com.sergei.pokhodai.expensemanagement.database.api.dao.UserDao
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class UserRepository(
    private val userDao: UserDao,
    private val userEntityMapper: UserEntityMapper,
) {

    suspend fun setUser(model: UserSelfModel) {
        return withContext(Dispatchers.IO) {
            userDao.insert(userEntityMapper.mapModelToEntity(model))
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            userDao.deleteAllUsers()
        }
    }

    suspend fun setAndGetUser(model: UserSelfModel): UserSelfModel {
        return withContext(Dispatchers.IO) {
            val id = userDao.insertAndGet(userEntityMapper.mapModelToEntity(model))
            return@withContext getUserById(id)
        }
    }

    suspend fun setUpdateAndGetUser(model: UserSelfModel): UserSelfModel {
        setUpdateUser(model)
        return model
    }

    suspend fun getUserList(): List<UserSelfModel> {
        return withContext(Dispatchers.IO) {
            userDao.getAll().map(userEntityMapper::mapEntityToModel)
        }
    }

    suspend fun setUpdateUser(model: UserSelfModel) {
        return withContext(Dispatchers.IO) {
            userDao.update(userEntityMapper.mapModelToEntity(model))
        }
    }

    suspend fun deleteUserById(userId: Long) {
        return withContext(Dispatchers.IO) {
            userDao.deleteByUserId(userId)
        }
    }

    suspend fun getUserById(userId: Long): UserSelfModel {
        return withContext(Dispatchers.IO) {
            userDao.getByUserId(userId)?.let(userEntityMapper::mapEntityToModel) ?: throw Throwable()
        }
    }
}