package com.sergei.pokhodai.expensemanagement.feature.category.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryEditorContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryIconDialogContract

internal class CategoryBottomNavigationVisibleProviderImpl : BottomNavigationVisibleProvider {

    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            CategoryDialogContract.serializer().generateHashCode(),
            CategoryEditorContract.serializer().generateHashCode(),
            CategoryIconDialogContract.serializer().generateHashCode(),
        )
    }
}