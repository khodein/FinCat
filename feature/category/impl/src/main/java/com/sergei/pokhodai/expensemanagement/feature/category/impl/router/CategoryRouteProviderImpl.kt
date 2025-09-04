package com.sergei.pokhodai.expensemanagement.feature.category.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.CategoryDialog
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.CategoryEditorFragment
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.CategoryIconDialog
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryEditorContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryIconDialogContract

class CategoryRouteProviderImpl : RouteProvider {

    override fun getDestination(): List<RouteDestination> {
        return listOf(
            RouteDestination(
                type = RouteDestination.Type.FragmentType(
                    clazz = CategoryEditorFragment::class,
                    contract = CategoryEditorContract::class
                ),
            ),
            RouteDestination(
                type = RouteDestination.Type.DialogType(
                    clazz = CategoryDialog::class,
                    contract = CategoryDialogContract::class
                ),
            ),
            RouteDestination(
                type = RouteDestination.Type.DialogType(
                    clazz = CategoryIconDialog::class,
                    contract = CategoryIconDialogContract::class
                )
            )
        )
    }
}