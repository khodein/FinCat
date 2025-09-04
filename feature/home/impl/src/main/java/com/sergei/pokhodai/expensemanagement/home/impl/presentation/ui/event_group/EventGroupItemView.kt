package com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_group

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItemView
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_8_8_8_8
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.corner.BorderType
import com.sergei.pokhodai.expensemanagement.core.base.corner.RoundMode
import com.sergei.pokhodai.expensemanagement.core.base.corner.ViewCorner
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.getColor
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItemView

internal class EventGroupItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), RecyclerItemView<EventGroupItem.State> {

    private var cacheHeaderView: HeaderItemView? = null
    private val cacheViews: HashMap<String, EventItemView> = hashMapOf()

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        orientation = VERTICAL

        applyPadding(P_8_8_8_8)

        ViewCorner.Border(
            radius = RADIUS.value,
            roundMode = RoundMode.ALL,
            borderType = BorderType.Simple(
                strokeWidth = STROKE_WIDTH.value,
                strokeColor = getColor(baseR.color.grey_300)
            )
        )
            .resolve(
                view = this,
                backgroundColorInt = getColor(baseR.color.background)
            )
    }

    override fun bindState(state: EventGroupItem.State) {
        removeAllViews()
        bindHeaderState(state.eventHeaderState)
        bindEventList(state.eventStateList)
    }

    private fun bindEventList(list: List<EventItem.State>) {
        list.forEach {
            val view = cacheViews.getOrPut(it.provideId) {
                EventItemView(context).apply {
                    layoutParams = LayoutParams(
                        MATCH_PARENT,
                        WRAP_CONTENT
                    )
                }
            }

            view.bindState(it)
            addView(view)
        }
    }

    private fun bindHeaderState(headerState: HeaderItem.State) {
        val headerView = cacheHeaderView ?: HeaderItemView(context).apply {
            layoutParams = LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )
        }.also {
            cacheHeaderView = it
        }.apply {
            bindState(headerState)
        }
        addView(headerView)
    }

    private companion object {
        val RADIUS = ViewDimension.Dp(8)
        val STROKE_WIDTH = ViewDimension.Dp(1)
    }
}