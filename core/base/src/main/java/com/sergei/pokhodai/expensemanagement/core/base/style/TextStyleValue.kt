package com.sergei.pokhodai.expensemanagement.core.base.style

import androidx.annotation.StyleRes

sealed interface TextStyleValue {
    data class Res(
        @StyleRes val value: Int,
    ) : TextStyleValue
}