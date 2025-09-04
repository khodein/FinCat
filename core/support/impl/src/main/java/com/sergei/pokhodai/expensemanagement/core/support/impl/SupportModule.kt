package com.sergei.pokhodai.expensemanagement.core.support.impl

import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object SupportModule {
    fun get(): Module {
        return module {
            singleOf(::ResManagerImpl) bind ResManager::class
            singleOf(::LocaleManagerImpl) bind LocaleManager::class
        }
    }
}