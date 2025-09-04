package com.sergei.pokhodai.expensemanagement.home.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.home.impl.router.contract.TabHomeContract
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

internal class HomeTabProviderImpl : TabProvider {

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    override fun getDestination(): List<TabDestination> {
        val homeTab = TabHomeContract
        val clazzHomeTab = homeTab::class
        return listOf(
            TabDestination(
                order = 2,
                id = clazzHomeTab.serializer().generateHashCode(),
                clazz = clazzHomeTab,
                tab = homeTab,
                isStart = false
            )
        )
    }
}