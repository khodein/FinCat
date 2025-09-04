package com.sergei.pokhodai.expensemanagement.core.router.support.alert

class AlertRouterModel(
    val text: String,
    val positiveBtnText: String,
    val negativeBtnText: String,
    val listenerNegative: () -> Unit,
    val listenerPositive: () -> Unit,
)