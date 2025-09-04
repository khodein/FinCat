package com.sergei.pokhodai.expensemanagement.core.router.destination

import com.sergei.pokhodai.expensemanagement.core.router.contract.TabContract
import kotlin.reflect.KClass

data class TabDestination(
    val id: Int,
    val order: Int,
    val isStart: Boolean,
    val clazz: KClass<out TabContract>,
    val tab: TabContract,
)