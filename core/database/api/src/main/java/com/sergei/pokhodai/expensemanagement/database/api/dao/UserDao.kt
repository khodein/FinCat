package com.sergei.pokhodai.expensemanagement.database.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sergei.pokhodai.expensemanagement.database.api.entity.UserSelfEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM `fincatdatabase_user_table`")
    suspend fun getAll(): List<UserSelfEntity>

    @Insert
    suspend fun insertAll(vararg entities: UserSelfEntity)

    @Insert
    suspend fun insert(entity: UserSelfEntity)

    @Query("DELETE FROM `fincatdatabase_user_table`")
    suspend fun deleteAllUsers()

    @Insert
    suspend fun insertAndGet(entity: UserSelfEntity): Long

    @Update
    suspend fun update(entity: UserSelfEntity)

    @Query("DELETE FROM fincatdatabase_user_table WHERE id = :userId")
    suspend fun deleteByUserId(userId: Long)

    @Query("SELECT * FROM fincatdatabase_user_table WHERE id = :userId")
    suspend fun getByUserId(userId: Long): UserSelfEntity?
}