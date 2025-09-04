package com.sergei.pokhodai.expensemanagement.home.impl.presentation.mapper

import androidx.core.text.buildSpannedString
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_24
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.utils.getFormatCurrency
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.spanned.foreground
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.AmountModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.DateModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.home.impl.R
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event.EventItem
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_group.EventGroupItem
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.ui.event_sum.EventSumItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import java.math.BigDecimal

internal class HomeMapper(
    private val categoryTypeMapper: CategoryTypeMapper,
    private val categoryNameMapper: CategoryNameMapper,
    private val resManager: ResManager,
) {
    fun getBottomButtonState(
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "home_add_new_id",
            fill = ButtonItem.Fill.Filled,
            radius = ViewDimension.Dp(40),
            height = ViewDimension.Dp(48),
            container = ButtonItem.Container(
                paddings = P_0_0_0_24
            ),
            icon = ImageValue.Res(R.drawable.ic_add_plus_circle),
            value = resManager.getString(R.string.home_add),
            onClick = onClick
        )
    }

    fun getEventStateList(
        data: Map<DateModel, List<EventModel>>,
        onClickEvent: (state: EventItem.State) -> Unit
    ): List<RecyclerState> {
        if (data.isEmpty()) {
            return emptyList()
        }

        val amounts = mutableListOf<SumAmountModel>()
        return buildList {
            data.onEachIndexed { index, model ->
                val provideId = index.toString()

                val sumAmountList = model.value.map { value ->
                    SumAmountModel(
                        amountModel = value.amountModel,
                        budgetType = value.budgetType
                    )
                }.apply {
                    amounts.addAll(this)
                }

                val eventHeaderState = getEventHeaderItemState(
                    dateModel = model.key,
                    sumAmountList = sumAmountList
                )

                val eventStateList = getEventListStates(
                    items = model.value,
                    onClickEvent = onClickEvent
                )

                EventGroupItem.State(
                    provideId = provideId,
                    eventHeaderState = eventHeaderState,
                    eventStateList = eventStateList
                ).let(::add)
            }

            getEventSumItemState(amounts)?.let {
                add(0, it)
            }
        }
    }

    private fun getEventSumItemState(amounts: MutableList<SumAmountModel>): EventSumItem.State? {
        if (amounts.isEmpty()) {
            return null
        }

        val expense = mutableListOf<SumAmountModel>()
        val income = mutableListOf<SumAmountModel>()
        val balance = mutableListOf<SumAmountModel>()

        amounts.forEach {
            val model = when (it.budgetType) {
                BudgetType.INCOME -> {
                    income.add(it)
                    it
                }

                BudgetType.EXPENSE -> {
                    val model = SumAmountModel(
                        amountModel = it.amountModel.copy(value = it.amountModel.value.negate()),
                        budgetType = it.budgetType
                    )
                    expense.add(model)
                    model
                }
            }
            balance.add(model)
        }

        val sumExpense = expense.sumOf { it.amountModel.value }.toString().getFormatCurrency()
        val sumBalance = balance.sumOf { it.amountModel.value }.toString().getFormatCurrency()
        val sumIncome = income.sumOf { it.amountModel.value }.toString().getFormatCurrency()

        val expenseText = buildSpannedString {
            foreground(resManager.getColor(baseR.color.red_600)) {
                append(sumExpense.first)
            }
        }

        val balanceText = buildSpannedString {
            foreground(resManager.getColor(baseR.color.grey_900)) {
                append(sumBalance.first)
            }
        }

        val incomeText = buildSpannedString {
            foreground(resManager.getColor(baseR.color.green_600)) {
                append(sumIncome.first)
            }
        }

        return EventSumItem.State(
            provideId = "event_home_sum_item_id",
            expenseText = expenseText,
            balanceText = balanceText,
            incomeText = incomeText,
        )
    }

    private fun getEventListStates(
        items: List<EventModel>,
        onClickEvent: (state: EventItem.State) -> Unit
    ): List<EventItem.State> {
        return items.map { model ->
            getEventState(
                model = model,
                onClickEvent = onClickEvent
            )
        }
    }

    private fun getEventHeaderItemState(
        dateModel: DateModel,
        sumAmountList: List<SumAmountModel>
    ): HeaderItem.State {
        val amountSum = getAmountSum(sumAmountList).toString().getFormatCurrency()
        val budgetType = if (amountSum.second > BigDecimal.ZERO) {
            BudgetType.INCOME
        } else {
            BudgetType.EXPENSE
        }

        val amount = getAmount(
            budgetType = budgetType,
            model = AmountModel(
                value = amountSum.second,
                format = amountSum.first
            )
        )
        return HeaderItem.State(
            provideId = "event_header_item_id",
            title = getTitle(dateModel),
            sum = amount.toString()
        )
    }

    private fun getTitle(
        dateModel: DateModel
    ): String {
        return when {
            dateModel.value.isYesterday() -> resManager.getString(R.string.home_item_header_yesterday)
            dateModel.value.isToday() -> resManager.getString(R.string.home_item_header_today)

            else -> dateModel.value.dd_MM_yyyy()
        }
    }

    private fun getAmountSum(
        list: List<SumAmountModel>,
    ): BigDecimal {
        return list.map {
            when (it.budgetType) {
                BudgetType.INCOME -> it.amountModel.value
                BudgetType.EXPENSE -> it.amountModel.value.negate()
            }
        }.sumOf { it }
    }

    private fun getEventState(
        model: EventModel,
        onClickEvent: (state: EventItem.State) -> Unit,
    ): EventItem.State {
        return EventItem.State(
            provideId = model.id.toString(),
            categoryKindItemState = mapCategoryKindState(model.categoryModel),
            name = categoryNameMapper.getName(model = model.categoryModel),
            data = model,
            onClick = onClickEvent,
            amount = getAmount(
                budgetType = model.budgetType,
                model = model.amountModel,
            )
        )
    }

    private fun mapCategoryKindState(
        model: CategoryModel?,
    ): CategoryKindItem.State {
        return CategoryKindItem.State(
            provideId = "category_home_kind_item_id",
            icon = model?.type?.let(categoryTypeMapper::getImageResId)?.let(ImageValue::Res),
            color = ColorValue.parseColor(model?.colorName),
        )
    }

    private fun getAmount(
        budgetType: BudgetType,
        model: AmountModel
    ): CharSequence {
        return buildSpannedString {
            val (prefix, colorRes) = when (budgetType) {
                BudgetType.INCOME -> {
                    "" to baseR.color.green_600
                }

                BudgetType.EXPENSE -> {
                    (if (model.value > BigDecimal.ZERO) "-" else "") to baseR.color.red_600
                }
            }
            foreground(color = resManager.getColor(colorRes)) {
                append(prefix)
                append(model.format)
            }
        }
    }

    fun getCalendarText(date: LocalDateFormatter): String {
        return date.formatDateToLocalizedMonthYear()
    }

    fun getEmptyText(): String {
        return resManager.getString(R.string.home_items_empty)
    }

    fun getErrorText(): String {
        return resManager.getString(R.string.home_global_error)
    }

    private class SumAmountModel(
        val amountModel: AmountModel,
        val budgetType: BudgetType,
    )
}