package com.sergei.pokhodai.expensemanagement.core.router.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlin.reflect.KClass

interface TabContract {
    val startDestination: KClass<out RouterContract>

    @get:StringRes
    val nameResId: Int

    @get:DrawableRes
    val iconResId: Int
}