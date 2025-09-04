package com.sergei.pokhodai.expensemanagement.feature.report.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.feature.report.impl.router.contract.TabReportContract
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

internal class ReportTabProviderImpl : TabProvider {

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    override fun getDestination(): List<TabDestination> {
        val reportTab = TabReportContract
        val clazzReportTab = reportTab::class
        return listOf(
            TabDestination(
                order = 1,
                id = clazzReportTab.serializer().generateHashCode(),
                clazz = clazzReportTab,
                tab = reportTab,
                isStart = true
            )
        )
    }
}