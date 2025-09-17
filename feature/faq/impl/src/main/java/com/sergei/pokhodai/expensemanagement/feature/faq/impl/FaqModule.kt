package com.sergei.pokhodai.expensemanagement.feature.faq.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.faq.api.FaqRouter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository.FaqRepository
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.FaqViewModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui.FaqItem
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.ui.FaqItemView
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.router.FaqBottomNavigationVisibilityProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.router.FaqRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.router.FaqRouterProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object FaqModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = FaqItem.State::class.java, onView = ::FaqItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::FaqRepository)
            singleOf(::FaqRouterImpl) bind FaqRouter::class
            singleOf(::FaqRouterProviderImpl) bind RouteProvider::class
            singleOf(::FaqBottomNavigationVisibilityProviderImpl) bind BottomNavigationVisibleProvider::class

            viewModelOf(::FaqViewModel)
        }
    }
}