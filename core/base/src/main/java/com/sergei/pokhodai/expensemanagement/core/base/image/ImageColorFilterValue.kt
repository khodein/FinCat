package com.sergei.pokhodai.expensemanagement.core.base.image

import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue

sealed interface ImageColorFilterValue {
    data class Disabled(val alpha: Float = ALPHA_DISABLED) : ImageColorFilterValue
    data class Tint(
        val alpha: Float = ALPHA_ENABLED,
        val tintColor: ColorValue,
    ) : ImageColorFilterValue

    companion object {
        const val ALPHA_DISABLED = 0.4f
        const val ALPHA_ENABLED = 1f
    }
}