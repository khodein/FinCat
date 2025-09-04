package com.sergei.pokhodai.expensemanagement.core.base.corner

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.Px

class RoundOutlineProvider(
    private val mode: RoundMode,
    @Px private val radius: Int,
) : ViewOutlineProvider() {

    private val topOffset: Int
        get() = when (mode) {
            RoundMode.BOTTOM, RoundMode.NONE -> radius
            RoundMode.ALL, RoundMode.TOP -> 0
        }.toInt()

    private val bottomOffset: Int
        get() = when (mode) {
            RoundMode.BOTTOM, RoundMode.ALL -> 0
            RoundMode.TOP, RoundMode.NONE -> radius
        }.toInt()

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0,
            0 - topOffset,
            view.width,
            view.height + bottomOffset,
            radius.toFloat()
        )
    }
}