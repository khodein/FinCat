package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.router.ExchangeRateRouter
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data.ExchangeRateRepository
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.data.mapper.ExchangeRateResponseMapper
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ExchangeRateViewModel
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.mapper.ExchangeRateMapper
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ui.exchange_rate.ExchangeRateItem
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ui.exchange_rate.ExchangeRateItemView
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.ExchangeBottomNavigationVisibleProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.ExchangeRateRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.ExchangeRateRouterProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object ExchangeRateModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = ExchangeRateItem.State::class.java, onView = ::ExchangeRateItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::ExchangeRateRepository)
            singleOf(::ExchangeRateResponseMapper)

            singleOf(::ExchangeRateRouterImpl) bind ExchangeRateRouter::class
            singleOf(::ExchangeRateRouterProviderImpl) bind RouteProvider::class
            singleOf(::ExchangeBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class

            singleOf(::ExchangeRateMapper)
            viewModelOf(::ExchangeRateViewModel)
        }
    }
}