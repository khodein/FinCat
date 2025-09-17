package com.sergei.pokhodai.expensemanagement.feature.category.impl.router

import android.annotation.SuppressLint
import androidx.navigation.serialization.generateHashCode
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.contract.CategoryEditorContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.contract.CategoryIconDialogContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.contract.CategoryManagerContract

internal class CategoryBottomNavigationVisibleProviderImpl : BottomNavigationVisibleProvider {
    @SuppressLint("RestrictedApi")
    override fun getRouteListIsNotVisible(): List<Int> {
        return listOf(
            CategoryDialogContract.serializer().generateHashCode(),
            CategoryEditorContract.serializer().generateHashCode(),
            CategoryIconDialogContract.serializer().generateHashCode(),
            CategoryManagerContract.serializer().generateHashCode(),
        )
    }
}