package com.sergei.pokhodai.expensemanagement.feature.pincode.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.presentation.contract.PinCodeContract

internal class PinCodeBottomNavigationVisibleProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            PinCodeContract.serializer().generateHashCode()
        )
    }
}