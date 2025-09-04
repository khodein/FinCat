package com.sergei.pokhodai.expensemanagement.core.base.dimension

import com.sergei.pokhodai.expensemanagement.core.base.utils.dp

sealed interface ViewRect {
    val left: Int
    val top: Int
    val right: Int
    val bottom: Int

    data class Dp(
        val leftValue: Int,
        val topValue: Int,
        val rightValue: Int,
        val bottomValue: Int,
    ) : ViewRect {
        override val left: Int = leftValue.dp
        override val top: Int = topValue.dp
        override val right: Int = rightValue.dp
        override val bottom: Int = bottomValue.dp
    }
}