package com.sergei.pokhodai.expensemanagement.core.base.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.sergei.pokhodai.expensemanagement.core.base.R
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundOutlineProvider
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect

fun View.getColor(@ColorRes id: Int) = context.getColorInt(id)
fun View.getDrawable(@DrawableRes id: Int) = context.getResourceDrawable(id)
fun View.getString(@StringRes id: Int): String = context.getResourceString(id)
fun View.getColorStateList(@ColorRes id: Int): ColorStateList? = ContextCompat.getColorStateList(context, id)

fun View.makeRoundCorner(
    @Px radius: Int,
    mode: RoundMode,
) {
    outlineProvider = RoundOutlineProvider(
        mode = mode,
        radius = radius,
    )
    clipToOutline = mode != RoundMode.NONE
}

fun View.applyPadding(
    rect: ViewRect.Dp
) {
    applyPadding(
        left = rect.left,
        top = rect.top,
        right = rect.right,
        bottom = rect.bottom
    )
}
fun View.applyPadding(
    left: Int = this.paddingLeft,
    top: Int = this.paddingTop,
    right: Int = this.paddingRight,
    bottom: Int = this.paddingBottom
) {
    setPadding(left, top, right, bottom)
}

inline fun <STATE, VIEW : View> VIEW.bindStateOptional(
    state: STATE?,
    binder: VIEW.(STATE) -> Unit,
) {
    isVisible = state?.let {
        binder(state)
        true
    } ?: run {
        false
    }
}

fun View.resolveToLayoutParams(
    width: ViewDimension,
    height: ViewDimension,
) {
    resolveToLayoutParams(
        width = width.value,
        height = height.value
    )
}

fun View.resolveToLayoutParams(
    width: Int,
    height: Int,
) {
    if (layoutParams == null) {
        return
    }
    updateLayoutParams<LayoutParams> {
        this.width = width
        this.height = height
    }
}

fun View.makeRippleDrawable(
    rippleColorValue: ColorValue,
    backgroundColorValue: ColorValue = ColorValue.Color(Color.TRANSPARENT),
    disabledColorValue: ColorValue = backgroundColorValue,
    shapeDrawable: Int = GradientDrawable.RECTANGLE,
    topLeftCornerRadius: ViewDimension? = null,
    topRightCornerRadius: ViewDimension? = null,
    bottomLeftCornerRadius: ViewDimension? = null,
    bottomRightCornerRadius: ViewDimension? = null,
) {
    val rippleColor = rippleColorValue.getColor(context)
    val backgroundColor = backgroundColorValue.getColor(context)
    val disabledColor = disabledColorValue.getColor(context)

    fun GradientDrawable.applyCornerRadius() {
        val topLeft = topLeftCornerRadius?.value?.toFloat() ?: 0F
        val topRight = topRightCornerRadius?.value?.toFloat() ?: 0F
        val bottomLeft = bottomLeftCornerRadius?.value?.toFloat() ?: 0F
        val bottomRight = bottomRightCornerRadius?.value?.toFloat() ?: 0F

        cornerRadii = floatArrayOf(
            topLeft,
            topLeft,
            topRight,
            topRight,
            bottomRight,
            bottomRight,
            bottomLeft,
            bottomLeft,
        )
    }

    val content: GradientDrawable?
    val mask: GradientDrawable?

    if (backgroundColor == Color.TRANSPARENT) {
        content = null
        mask = GradientDrawable().apply {
            shape = shapeDrawable
            applyCornerRadius()
            setColor(getColor(R.color.grey_400))
        }
    } else {
        content = GradientDrawable().apply {
            shape = shapeDrawable
            applyCornerRadius()
            color = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(-android.R.attr.state_enabled)
                ),
                intArrayOf(
                    backgroundColor,
                    disabledColor
                )
            )
        }
        mask = null
    }

    foreground = RippleDrawable(
        ColorStateList.valueOf(rippleColor),
        content,
        mask
    )
}

fun View.clearRipple() {
    foreground = null
}

fun View.showSoftKeyboardPost() {
    post {
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun View.hideSoftKeyboardPost() {
    post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.showSoftKeyboard() {
    if (this.requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.setOnDebounceClick(debounceTime: Long = 300L, action: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                action(v)
            }

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}