package com.sergei.pokhodai.expensemanagement.core.base.corner

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.sergei.pokhodai.expensemanagement.core.base.drawable.BackgroundConsideringStrokeDrawable
import com.sergei.pokhodai.expensemanagement.core.base.drawable.BorderDrawable
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.makeRoundCorner

sealed interface ViewCorner {

    @get:Px
    val radius: Int
    val roundMode: RoundMode

    fun resolve(
        view: View,
        @ColorInt backgroundColorInt: Int,
    )

    data class Default(
        @Px override val radius: Int = 0,
        override val roundMode: RoundMode = RoundMode.NONE
    ) : ViewCorner {

        override fun resolve(
            view: View,
            backgroundColorInt: Int
        ) {
            view.overlay.clear()
            view.setBackgroundColor(backgroundColorInt)
            view.makeRoundCorner(
                radius = radius,
                mode = roundMode
            )
        }
    }

    data class Simple(
        @Px override val radius: Int,
        override val roundMode: RoundMode,
    ) : ViewCorner {
        override fun resolve(
            view: View,
            @ColorInt backgroundColorInt: Int,
        ) {
            view.overlay.clear()
            view.setBackgroundColor(backgroundColorInt)
            view.makeRoundCorner(
                radius = radius,
                mode = roundMode,
            )
        }
    }

    data class Border(
        @Px override val radius: Int,
        override val roundMode: RoundMode,
        val borderType: BorderType,
    ) : ViewCorner {

        override fun resolve(
            view: View,
            @ColorInt backgroundColorInt: Int,
        ) {
            val stroke = borderType.strokeWidth
            val strokeColor = borderType.strokeColor
            val radiusFloat = radius.toFloat()
            val isSide = borderType.isSide

            val borderDrawable = when (val type = borderType) {
                is BorderType.Simple -> {
                    BorderDrawable(
                        stroke = stroke.toFloat(),
                        strokeColor = strokeColor,
                        roundMode = roundMode,
                        radius = radiusFloat,
                        isSide = isSide,
                        isGapDash = false
                    )
                }

                is BorderType.Dashed -> {
                    val dash = type.dashLength.toFloat()
                    val gap = type.gapLength.toFloat()
                    BorderDrawable(
                        stroke = stroke.toFloat(),
                        strokeColor = strokeColor,
                        roundMode = roundMode,
                        radius = radiusFloat,
                        isSide = isSide,
                        dash = dash,
                        gap = gap,
                        isGapDash = true
                    )
                }
            }

            val arrayLayers = buildList {
                if (backgroundColorInt != Color.TRANSPARENT) {
                    BackgroundConsideringStrokeDrawable(
                        backgroundColorInt = backgroundColorInt,
                        stroke = stroke.toFloat(),
                        radius = radiusFloat,
                        roundMode = roundMode,
                        isSide = isSide
                    ).let(::add)
                }
                add(borderDrawable)
            }

            view.post {
                view.overlay.clear()
                view.overlay.add(borderDrawable)
            }

            view.background = LayerDrawable(arrayLayers.toTypedArray())

            view.makeRoundCorner(
                radius = radius,
                mode = roundMode,
            )

            view.applyPadding(
                top = (view.paddingTop - stroke) + stroke,
                bottom = (view.paddingBottom - stroke) + stroke,
                right = (view.paddingRight - stroke) + stroke,
                left = (view.paddingLeft - stroke) + stroke
            )
        }
    }
}