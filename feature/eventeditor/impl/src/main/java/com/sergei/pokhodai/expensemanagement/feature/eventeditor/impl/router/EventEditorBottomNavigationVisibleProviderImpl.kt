package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.router.contract.EventEditorRouterContract

internal class EventEditorBottomNavigationVisibleProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            EventEditorRouterContract.serializer().generateHashCode(),
        )
    }
}