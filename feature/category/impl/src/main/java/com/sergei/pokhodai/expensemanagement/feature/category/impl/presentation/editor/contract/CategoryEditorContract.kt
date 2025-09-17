package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.contract

import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryEditorContract(
    val id: Long? = null,
    val budgetType: String? = null,
    val isOpenFromDialog: Boolean = false,
) : RouterContract