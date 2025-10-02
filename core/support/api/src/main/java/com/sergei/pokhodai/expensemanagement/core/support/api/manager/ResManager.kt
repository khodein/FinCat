package com.sergei.pokhodai.expensemanagement.core.support.api.manager

import androidx.annotation.ColorRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResManager {
    fun getColor(@ColorRes colorResId: Int): Int
    fun getString(@StringRes stringResId: Int): String
    fun getString(@StringRes stringResId: Int, vararg args: Any?): String
    fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int): String
    fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int, vararg args: Any?): String
}