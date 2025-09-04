package com.sergei.pokhodai.expensemanagement.feature.category.api.mapper

import androidx.annotation.DrawableRes
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryType

interface CategoryTypeMapper {

    @DrawableRes
    fun getImageResId(type: CategoryType): Int

    fun getColorStr(type: CategoryType): String
}