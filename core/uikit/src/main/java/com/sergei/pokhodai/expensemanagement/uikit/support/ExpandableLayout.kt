package com.sergei.pokhodai.expensemanagement.uikit.support

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.sergei.pokhodai.expensemanagement.core.uikit.R

class ExpandableLayout : FrameLayout {
    private var state = State.EXPANDED
    private var expansion = 1f
        set(value) {
            if (field == value) {
                return
            }

            val delta = value - field
            state = when {
                value == 1f -> State.EXPANDED
                value == 0f -> State.COLLAPSED
                delta > 0f -> State.EXPANDING
                delta < 0f -> State.COLLAPSING
                else -> return
            }
            isVisible = state != State.COLLAPSED
            field = value
            requestLayout()
        }


    var orientation = Orientation.VERTICAL
    var duration = DEFAULT_DURATION
    private var interpolator = FastOutSlowInInterpolator()
    private var animator: ValueAnimator? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ExpandableLayout, defStyleAttr, 0
        )

        duration =
            a.getInt(R.styleable.ExpandableLayout_duration, DEFAULT_DURATION.toInt()).toLong()
        state = State.entries[a.getInt(
            R.styleable.ExpandableLayout_state,
            State.EXPANDED.ordinal
        )].also {
            expansion = when (it) {
                State.EXPANDED -> 1f
                State.COLLAPSED -> 0f
                else -> return@also
            }
        }
        orientation = Orientation.entries.toTypedArray()[a.getInt(
            R.styleable.ExpandableLayout_orientation,
            Orientation.VERTICAL.ordinal
        )]
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val bundle = Bundle()
        expansion = if (isExpanded) 1f else 0f
        bundle.putFloat(KEY_EXPANSION, expansion)
        bundle.putParcelable(KEY_SUPER_STATE, superState)
        return bundle
    }

    override fun onRestoreInstanceState(parcelable: Parcelable) {
        val bundle = parcelable as Bundle
        expansion = bundle.getFloat(KEY_EXPANSION)
        state = if (expansion == 1f) State.EXPANDED else State.COLLAPSED
        val superState = bundle.getParcelable<Parcelable>(KEY_SUPER_STATE)
        super.onRestoreInstanceState(superState)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        val size = if (orientation == Orientation.HORIZONTAL) width else height
        isVisible = !(expansion == 0f && size == 0)
        val expansionDelta = size - size * expansion
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            if (orientation == Orientation.HORIZONTAL) {
                var direction = -1
                if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    direction = 1
                }
                child.translationX = direction * expansionDelta
            } else {
                child.translationY = -expansionDelta
            }
        }
        if (orientation == Orientation.HORIZONTAL) {
            setMeasuredDimension(width - expansionDelta.toInt(), height)
        } else {
            setMeasuredDimension(width, height - expansionDelta.toInt())
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        animator?.cancel()
        super.onConfigurationChanged(newConfig)
    }

    val isExpanded: Boolean
        get() = state == State.EXPANDING || state == State.EXPANDED

    fun toggle(animate: Boolean = true) {
        if (isExpanded) {
            collapse(animate)
        } else {
            expand(animate)
        }
    }

    fun expand(animate: Boolean = true) {
        setExpanded(true, animate)
    }

    fun collapse(animate: Boolean = true) {
        setExpanded(false, animate)
    }

    fun setExpanded(expand: Boolean, animate: Boolean) {
        if (expand == isExpanded) {
            return
        }
        val targetExpansion = if (expand) 1 else 0
        if (animate) {
            animateSize(targetExpansion)
        } else {
            expansion = targetExpansion.toFloat()
        }
    }

    private fun animateSize(targetExpansion: Int) {
        animator?.cancel()

        var canceled = false
        animator = ValueAnimator.ofFloat(expansion, targetExpansion.toFloat()).apply {
            interpolator = this@ExpandableLayout.interpolator
            duration = this@ExpandableLayout.duration
            addUpdateListener { valueAnimator ->
                expansion = valueAnimator.animatedValue as Float
            }
            addListener(
                onCancel = {
                    canceled = true
                },
                onStart = {
                    state = if (targetExpansion == 0) State.COLLAPSING else State.EXPANDING
                },
                onEnd = {
                    if (!canceled) {
                        state =
                            if (targetExpansion == 0) State.COLLAPSED else State.EXPANDED
                        expansion = targetExpansion.toFloat()
                    }
                }
            )
            start()
        }
    }

    enum class State {
        EXPANDED,
        COLLAPSED,
        EXPANDING,
        COLLAPSING
    }

    enum class Orientation {
        VERTICAL,
        HORIZONTAL
    }

    companion object {
        const val KEY_SUPER_STATE = "super_state"
        const val KEY_EXPANSION = "expansion"
        private const val DEFAULT_DURATION = 3000L
    }
}