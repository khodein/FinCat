package com.sergei.pokhodai.expensemanagement.home.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.HomeViewModel
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.mapper.HomeMapper
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItem
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItemView
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_group.EventGroupItem
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_group.EventGroupItemView
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_sum.EventSumItem
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_sum.EventSumItemView
import com.sergei.pokhodai.expensemanagement.home.impl.router.HomeRouterProviderImpl
import com.sergei.pokhodai.expensemanagement.home.impl.router.HomeTabProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object HomeModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = EventItem.State::class.java, onView = ::EventItemView)
            .add(clazz = EventGroupItem.State::class.java, onView = ::EventGroupItemView)
            .add(clazz = EventSumItem.State::class.java, onView = ::EventSumItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::HomeTabProviderImpl) bind TabProvider::class
            singleOf(::HomeRouterProviderImpl) bind RouteProvider::class

            singleOf(::HomeMapper)
            viewModelOf(::HomeViewModel)
        }
    }
}