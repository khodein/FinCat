package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.router.contract.CalendarMonthContract

internal class CalendarMonthBottomNavigationVisibleProviderImpl: BottomNavigationVisibleProvider {
    
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf()
    }
}