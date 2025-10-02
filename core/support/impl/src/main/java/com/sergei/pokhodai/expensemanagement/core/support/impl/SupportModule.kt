package com.sergei.pokhodai.expensemanagement.core.support.impl

import com.sergei.pokhodai.expensemanagement.core.support.api.manager.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.core.support.api.router.SupportRouter
import com.sergei.pokhodai.expensemanagement.core.support.impl.manager.LocaleManagerImpl
import com.sergei.pokhodai.expensemanagement.core.support.impl.manager.ResManagerImpl
import com.sergei.pokhodai.expensemanagement.core.support.impl.router.SupportRouterImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object SupportModule {
    fun get(): Module {
        return module {
            singleOf(::ResManagerImpl) bind ResManager::class
            singleOf(::LocaleManagerImpl) bind LocaleManager::class
            singleOf(::SupportRouterImpl) bind SupportRouter::class
        }
    }
}