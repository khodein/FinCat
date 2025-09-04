package com.sergei.pokhodai.expensemanagement.feature.category.impl.router.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryEditorContract(
    val id: Int? = null,
    val budgetType: String,
    val isOpenFromDialog: Boolean = false,
) : RouterContract