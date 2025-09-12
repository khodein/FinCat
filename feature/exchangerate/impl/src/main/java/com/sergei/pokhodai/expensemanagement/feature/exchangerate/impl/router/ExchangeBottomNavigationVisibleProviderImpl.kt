package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.router.contact.ExchangeRateContract

internal class ExchangeBottomNavigationVisibleProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            ExchangeRateContract.serializer().generateHashCode(),
        )
    }
}