package com.sergei.pokhodai.expensemanagement.core.base.spanned

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class SpannedTextClickableSpan(
    val isUnderline: Boolean = false,
    val onClick: (() -> Unit)?,
) : ClickableSpan() {

    override fun onClick(widget: View) {
        onClick?.invoke()
    }

    override fun updateDrawState(paint: TextPaint) {
        paint.isUnderlineText = isUnderline
    }
}