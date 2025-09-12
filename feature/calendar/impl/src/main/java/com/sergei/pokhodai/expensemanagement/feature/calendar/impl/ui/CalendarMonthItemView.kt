package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItemView

internal class CalendarMonthItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), RecyclerItemView<CalendarMonthItem.State> {

    private val tagHeight = ViewDimension.Dp(40)
    private val tagWidth = ViewDimension.Dp(80)

    private val tagItemView: TagItemView

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        applyPadding(P_8_8_8_8)

        tagItemView = TagItemView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                tagWidth.value,
                tagHeight.value
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        addView(tagItemView)
    }

    override fun bindState(state: CalendarMonthItem.State) {
        tagItemView.bindState(
            state.tagItemState.copy(
                container = TagItem.Container(
                    width = tagWidth,
                    height = tagHeight
                )
            )
        )
    }
}