package com.sergei.pokhodai.expensemanagement.core.base.corner

enum class RoundMode {
    ALL,
    TOP,
    BOTTOM,
    NONE,
}

fun RoundMode.getRadiiForRoundMode(
    cornerRadius: Float,
): FloatArray {
    return when (this) {
        RoundMode.ALL -> floatArrayOf(
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius
        )

        RoundMode.TOP -> floatArrayOf(
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            0f,
            0f,
            0f,
            0f
        )

        RoundMode.BOTTOM -> floatArrayOf(
            0f,
            0f,
            0f,
            0f,
            cornerRadius,
            cornerRadius,
            cornerRadius,
            cornerRadius
        )

        RoundMode.NONE -> FloatArray(8)
    }
}