package com.sergei.pokhodai.expensemanagement.core.eventbus.impl

import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object EventBusModule {
    fun get(): Module {
        return module {
            singleOf(::EventBusImpl) bind EventBus::class
        }
    }
}