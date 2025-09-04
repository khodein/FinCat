package com.sergei.pokhodai.expensemanagement.core.base.image

import androidx.annotation.DrawableRes

sealed interface ImageValue {
    data class Res(
        @DrawableRes val value: Int,
    ) : ImageValue
}