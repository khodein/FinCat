package com.sergei.pokhodai.expensemanagement.feature.report.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.feature.report.impl.data.report.ReportRepository
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ReportViewModel
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.mapper.ReportMapper
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.report.ReportItem
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.report.ReportItemView
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic.StatisticItem
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic.StatisticItemView
import com.sergei.pokhodai.expensemanagement.feature.report.impl.router.ReportRouterProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.report.impl.router.ReportTabProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object ReportModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = ReportItem.State::class.java, onView = ::ReportItemView)
            .add(clazz = StatisticItem.State::class.java, onView = ::StatisticItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::ReportRepository)
            singleOf(::ReportMapper)
            viewModelOf(::ReportViewModel)

            singleOf(::ReportRouterProviderImpl) bind RouteProvider::class
            singleOf(::ReportTabProviderImpl) bind TabProvider::class
        }
    }
}