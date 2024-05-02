package com.sergei.pokhodai.expensemanagement.resourse

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes stringResId: Int) = context.getString(stringResId)
    fun getString(@StringRes stringResId: Int, vararg args: Any?) = context.getString(stringResId, *args)
}