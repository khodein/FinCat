package com.sergei.pokhodai.expensemanagement.feature.category.impl.router

import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryDialogContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryEditorContract
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract.CategoryIconDialogContract

internal class CategoryRouterImpl(
    private val router: Router
) : CategoryRouter {

    override fun goToCategoryEditor(
        id: Long?,
        budgetType: String,
        isOpenFromDialog: Boolean
    ) {
        router.navigate(
            contract = CategoryEditorContract(
                id = id,
                budgetType = budgetType,
                isOpenFromDialog = isOpenFromDialog,
            ),
            navAnimation = NavAnimation.FADE
        )
    }

    override fun goToCategoryDialog(budgetType: String) {
        router.navigate(contract = CategoryDialogContract(budgetType = budgetType))
    }

    override fun goToCategoryIcon() {
        router.navigate(contract = CategoryIconDialogContract)
    }
}