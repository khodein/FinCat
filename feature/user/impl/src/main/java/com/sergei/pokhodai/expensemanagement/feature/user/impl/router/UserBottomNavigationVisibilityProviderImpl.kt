package com.sergei.pokhodai.expensemanagement.feature.user.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserCurrencyContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserLanguageContract
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserRouterContract

internal class UserBottomNavigationVisibilityProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            UserRouterContract.serializer().generateHashCode(),
            UserLanguageContract.serializer().generateHashCode(),
            UserCurrencyContract.serializer().generateHashCode(),
        )
    }
}