package com.sergei.pokhodai.expensemanagement.database.api.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sergei.pokhodai.expensemanagement.database.api.entity.EventEntity

@Dao
interface EventDao {

    @Insert
    suspend fun insertAll(vararg entities: EventEntity)

    @Update
    suspend fun update(entity: EventEntity)

    @Query("DELETE FROM fincatdatabase_event_table WHERE id = :id AND userId = :userId")
    suspend fun deleteById(id: Int, userId: Int)

    @Query("SELECT * FROM fincatdatabase_event_table WHERE id = :id AND userId = :userId")
    suspend fun getById(id: Int, userId: Int): EventEntity?

    @Query("SELECT * FROM fincatdatabase_event_table WHERE dateMonth = :dateMonth AND dateYear = :dateYear AND userId = :userId ORDER BY dateDay DESC")
    suspend fun getEventByMonthAndYear(
        userId: Int,
        dateMonth: String,
        dateYear: String,
    ): List<EventEntity>

    @Query("SELECT * FROM fincatdatabase_event_table WHERE dateMonth = :dateMonth AND dateYear = :dateYear AND budgetType = :budgetType AND userId = :userId ORDER BY dateDay DESC")
    suspend fun getEventByMonthAndYearAndBudgetType(
        userId: Int,
        dateMonth: String,
        dateYear: String,
        budgetType: String,
    ): List<EventEntity>
}