package com.sergei.pokhodai.expensemanagement.core.base.spanned

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.text.inSpans

inline fun SpannableStringBuilder.foreground(
    @ColorInt color: Int = Color.BLACK,
    builderAction: SpannableStringBuilder.() -> Unit,
): SpannableStringBuilder = inSpans(ForegroundColorSpan(color), builderAction = builderAction)

inline fun SpannableStringBuilder.clickable(
    noinline onClick: (() -> Unit)?,
    builderAction: SpannableStringBuilder.() -> Unit,
): SpannableStringBuilder = inSpans(
    span = SpannedTextClickableSpan(isUnderline = false, onClick = onClick),
    builderAction = builderAction
)