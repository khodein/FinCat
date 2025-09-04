package com.sergei.pokhodai.expensemanagement.database.impl

import android.content.Context
import androidx.room.Room
import com.sergei.pokhodai.expensemanagement.database.api.key.DatabaseKey
import org.koin.core.module.Module
import org.koin.dsl.module

object RoomModule {
    fun get(): Module {
        return module {
            single {
                Room.databaseBuilder(
                    get<Context>(),
                    Database::class.java,
                    DatabaseKey.DATABASE_NAME,
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()
            }

            single { get<Database>().getCategoryDao() }
            single { get<Database>().getEventDao() }
            single { get<Database>().getUserDao() }
        }
    }
}