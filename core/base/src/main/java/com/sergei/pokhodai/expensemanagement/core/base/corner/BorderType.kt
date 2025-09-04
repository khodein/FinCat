package com.sergei.pokhodai.expensemanagement.core.base.corner

import androidx.annotation.ColorInt
import androidx.annotation.Px

sealed interface BorderType {
    @get:Px
    val strokeWidth: Int

    @get:ColorInt
    val strokeColor: Int
    val isSide: Boolean

    data class Simple(
        @Px override val strokeWidth: Int,
        @ColorInt override val strokeColor: Int,
        override val isSide: Boolean = false,
    ) : BorderType

    data class Dashed(
        @Px override val strokeWidth: Int,
        @ColorInt override val strokeColor: Int,
        override val isSide: Boolean = false,
        @Px val gapLength: Int,
        @Px val dashLength: Int,
    ) : BorderType
}