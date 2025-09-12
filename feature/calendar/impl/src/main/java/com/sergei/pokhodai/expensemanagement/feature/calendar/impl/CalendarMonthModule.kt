package com.sergei.pokhodai.expensemanagement.feature.calendar.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.calendar.api.CalendarMonthRouter
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.CalendarMonthViewModel
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.mapper.CalendarMonthMapper
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.mapper.CalendarMonthNameMapperImpl
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.CalendarMonthBottomNavigationVisibleProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.CalendarMonthRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.CalendarMonthRouterProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.ui.CalendarMonthItem
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.ui.CalendarMonthItemView
import com.sergei.pokhodai.expensemanagement.feature.calendar.presentation.mapper.CalendarMonthNameMapper
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object CalendarMonthModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = CalendarMonthItem.State::class.java, onView = ::CalendarMonthItemView)
            .build()
    }

    fun get() = module {
        singleOf(::CalendarMonthRouterImpl) bind CalendarMonthRouter::class
        singleOf(::CalendarMonthRouterProviderImpl) bind RouteProvider::class
        singleOf(::CalendarMonthBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class

        singleOf(::CalendarMonthNameMapperImpl) bind CalendarMonthNameMapper::class
        singleOf(::CalendarMonthMapper)
        viewModelOf(::CalendarMonthViewModel)
    }
}