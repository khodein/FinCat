package com.sergei.pokhodai.expensemanagement.database.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sergei.pokhodai.expensemanagement.database.api.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM `fincatdatabase_category_table` WHERE budgetType = :budgetType AND userId = :userId")
    suspend fun getAllByBudgetType(
        userId: Long,
        budgetType: String
    ): List<CategoryEntity>
    @Insert
    suspend fun insertAll(vararg entities: CategoryEntity)

    @Query("DELETE FROM `fincatdatabase_category_table`")
    suspend fun deleteAll()
    @Update
    suspend fun update(entity: CategoryEntity)
    @Query("SELECT * FROM fincatdatabase_category_table WHERE id = :id AND userId = :userId")
    suspend fun getById(
        id: Long,
        userId: Long,
    ): CategoryEntity?

    @Query("DELETE FROM fincatdatabase_category_table WHERE id = :id AND userId = :userId")
    suspend fun deleteById(
        id: Long,
        userId: Long,
    )
}