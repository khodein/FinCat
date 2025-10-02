package com.sergei.pokhodai.expensemanagement.feature.pincode.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.pincode.api.PinCodeRouter
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.PinCodeViewModel
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num.item.NumItem
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.ui.num.item.NumItemView
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router.PinCodeBottomNavigationVisibleProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router.PinCodeRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router.PinCodeRouterProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object PinCodeModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = NumItem.State::class.java, onView = ::NumItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::PinCodeRouterImpl) bind PinCodeRouter::class
            singleOf(::PinCodeRouterProviderImpl) bind RouteProvider::class
            singleOf(::PinCodeBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class

            viewModelOf(::PinCodeViewModel)
        }
    }
}