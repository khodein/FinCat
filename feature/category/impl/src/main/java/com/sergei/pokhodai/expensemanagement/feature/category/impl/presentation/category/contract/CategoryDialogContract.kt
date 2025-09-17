package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryDialogContract(
    val budgetType: String,
) : RouterContract