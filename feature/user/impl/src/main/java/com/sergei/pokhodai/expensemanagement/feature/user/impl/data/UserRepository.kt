package com.sergei.pokhodai.expensemanagement.feature.user.impl.data

import com.sergei.pokhodai.expensemanagement.database.api.dao.UserDao
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserSelfModel
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

    suspend fun deleteUserById(userId: Int) {
        return withContext(Dispatchers.IO) {
            userDao.deleteByUserId(userId)
        }
    }

    suspend fun getUserById(userId: Int): UserSelfModel? {
        return withContext(Dispatchers.IO) {
            userDao.getByUserId(userId)?.let(userEntityMapper::mapEntityToModel)
        }
    }
}