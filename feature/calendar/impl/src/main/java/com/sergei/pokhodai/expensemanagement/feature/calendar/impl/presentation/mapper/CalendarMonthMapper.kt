package com.sergei.pokhodai.expensemanagement.feature.calendar.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_4_16_16
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.calendar.domain.model.CalendarMonthModel
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.R
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.ui.CalendarMonthItem
import com.sergei.pokhodai.expensemanagement.feature.calendar.presentation.mapper.CalendarMonthNameMapper
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem

internal class CalendarMonthMapper(
    private val resManager: ResManager,
    private val calendarMonthNameMapper: CalendarMonthNameMapper,
) {
    fun getItems(
        month: CalendarMonthModel,
        list: List<CalendarMonthModel>,
        onClickTag: (state: TagItem.State) -> Unit
    ): List<RecyclerState> {
        return list.map { model ->
            CalendarMonthItem.State(
                provideId = model.name,
                tagItemState = getTagItemState(
                    month = month,
                    model = model,
                    onClickTag = onClickTag,
                )
            )
        }
    }

    private fun getTagItemState(
        month: CalendarMonthModel,
        model: CalendarMonthModel,
        onClickTag: (state: TagItem.State) -> Unit
    ): TagItem.State {
        return TagItem.State(
            provideId = month.name,
            data = model,
            value = calendarMonthNameMapper.getShortName(model),
            isActive = month == model,
            onClick = onClickTag
        )
    }

    fun getButtonState(
        onClick: ((state: ButtonItem.State) -> Unit)? = null
    ): ButtonItem.State? {
        return ButtonItem.State(
            provideId = "category_editor_button_item",
            width = ViewDimension.WrapContent,
            height = ViewDimension.Dp(40),
            radius = ViewDimension.Dp(84),
            container = ButtonItem.Container(
                paddings = P_16_4_16_16,
                backgroundColor = ColorValue.Res(baseR.color.background)
            ),
            fill = ButtonItem.Fill.Filled,
            value = resManager.getString(R.string.calendar_month_button_edit),
            onClick = onClick
        )
    }
}