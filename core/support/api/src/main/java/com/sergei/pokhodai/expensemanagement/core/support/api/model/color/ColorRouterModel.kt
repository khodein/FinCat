package com.sergei.pokhodai.expensemanagement.core.support.api.model.color

class ColorRouterModel(
    val cancelText: String,
    val confirmText: String,
    val onClickColor: (String) -> Unit
)