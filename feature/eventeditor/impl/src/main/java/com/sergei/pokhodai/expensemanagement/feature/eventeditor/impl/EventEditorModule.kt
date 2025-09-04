package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetCategoryEventByMonthAndYearAndBudgetTypeUceCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.usecase.GetDateEventByMonthAndYearUseCase
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.router.EventEditorRouter
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.EventRepository
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.data.mapper.EventEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.domain.usecase.GetCategoryEventByMonthAndYearAndBudgetTypeImpl
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.domain.usecase.GetDateEventByMonthAndYearUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.EventEditorViewModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.mapper.EventEditorMapper
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.ui.event_category.EventEditorCategoryItem
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.ui.event_category.EventEditorCategoryItemView
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.EventEditorBottomNavigationVisibleProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.EventEditorRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.EventEditorRouterProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object EventEditorModule {

    init {
        RecyclerRegister.Builder()
            .add(
                clazz = EventEditorCategoryItem.State::class.java,
                onView = ::EventEditorCategoryItemView
            )
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::EventRepository)
            singleOf(::EventEditorMapper)
            singleOf(::EventEntityMapper)

            viewModelOf(::EventEditorViewModel)

            singleOf(::GetDateEventByMonthAndYearUseCaseImpl) bind GetDateEventByMonthAndYearUseCase::class
            singleOf(::GetCategoryEventByMonthAndYearAndBudgetTypeImpl) bind GetCategoryEventByMonthAndYearAndBudgetTypeUceCase::class

            singleOf(::EventEditorRouterImpl) bind EventEditorRouter::class
            singleOf(::EventEditorRouterProviderImpl) bind RouteProvider::class
            singleOf(::EventEditorBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class
        }
    }
}