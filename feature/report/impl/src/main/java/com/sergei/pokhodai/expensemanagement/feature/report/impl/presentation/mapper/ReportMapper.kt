package com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.mapper

import androidx.core.text.buildSpannedString
import com.sergei.pokhodai.expensemanagement.core.base.R as baseR
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_0_0_0_24
import com.sergei.pokhodai.expensemanagement.core.base.color.ColorValue
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewDimension
import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.getFormatCurrency
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.spanned.foreground
import com.sergei.pokhodai.expensemanagement.core.formatter.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.ResManager
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.BudgetType
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryModel
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.api.domain.model.EventModel
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.report.api.model.ReportListModel
import com.sergei.pokhodai.expensemanagement.feature.report.api.model.ReportModel
import com.sergei.pokhodai.expensemanagement.feature.report.impl.R
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.report.ReportItem
import com.sergei.pokhodai.expensemanagement.feature.report.impl.presentation.ui.statistic.StatisticItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.header.HeaderItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import java.math.BigDecimal

internal class ReportMapper(
    private val resManager: ResManager,
    private val categoryTypeMapper: CategoryTypeMapper,
    private val categoryNameMapper: CategoryNameMapper,
) {
    private val emptyMapState by lazy {
        mapOf(
            CategoryModel(
                type = null,
                budgetType = null,
                colorName = "#EEEEEE",
                name = ""
            ) to Pair(BigDecimal.ZERO, 20),
            CategoryModel(
                type = null,
                budgetType = null,
                colorName = "#E0E0E0",
                name = ""
            ) to Pair(BigDecimal.ZERO, 20),
            CategoryModel(
                type = null,
                budgetType = null,
                colorName = "#BDBDBD",
                name = ""
            ) to Pair(BigDecimal.ZERO, 20),
            CategoryModel(
                type = null,
                budgetType = null,
                colorName = "#9E9E9E",
                name = ""
            ) to Pair(BigDecimal.ZERO, 20),
            CategoryModel(
                type = null,
                budgetType = null,
                colorName = "#757575",
                name = ""
            ) to Pair(BigDecimal.ZERO, 20)
        )
    }
    fun getBottomButtonState(
        isLoading: Boolean,
        onClick: (state: ButtonItem.State) -> Unit
    ): ButtonItem.State {
        return ButtonItem.State(
            provideId = "home_add_new_id",
            fill = ButtonItem.Fill.Filled,
            isLoading = isLoading,
            radius = ViewDimension.Dp(40),
            height = ViewDimension.Dp(48),
            container = ButtonItem.Container(
                paddings = P_0_0_0_24
            ),
            icon = ImageValue.Res(R.drawable.ic_file_download_24),
            value = resManager.getString(R.string.report_bottom),
            onClick = onClick
        )
    }

    fun getItems(
        isExpense: Boolean,
        data: Map<CategoryModel, List<EventModel>>
    ): List<RecyclerState> {
        if (data.isEmpty()) {
            return emptyList()
        }

        val percentMap = calculateCategoryPercentages(data)
        val reportRetailsRes = if (isExpense) {
            R.string.report_expense_details
        } else {
            R.string.report_income_details
        }

        return buildList {
            getHeaderState(
                id = "report_header_details_id${isExpense}",
                text = resManager.getString(reportRetailsRes)
            ).let(::add)

            getStatisticState(isExpense = isExpense, percentMap = percentMap).let(::add)

            data.map {
                val sum = percentMap[it.key]
                val currencySymbol = it.value.firstOrNull()?.currencySymbol
                ReportItem.State(
                    provideId = it.key.name,
                    categoryKindItemState = mapCategoryKindState(it.key),
                    title = categoryNameMapper.getName(it.key),
                    subTitle = resManager.getString(
                        R.string.report_transactions,
                        "${it.value.size}"
                    ),
                    amount = sum?.first?.let { sum ->
                        getAmount(
                            sum = sum,
                            isExpense = isExpense,
                            currencySymbol = currencySymbol
                        )
                    } ?: "",
                    percent = "${sum?.second}%"
                )
            }.let(::addAll)
        }
    }

    fun getEmptyItems(): List<RecyclerState> {
        return listOf(
            getHeaderState(
                id = "empty_header",
                text = resManager.getString(R.string.report_header_empty)
            ),
            getStatisticState(
                isExpense = false,
                percentMap = emptyMapState
            ),
            RequestItem.State.Empty(
                message = getEmptyText(),
                container = RequestItem.State.Container(
                    width = ViewDimension.MatchParent,
                    height = ViewDimension.WrapContent,
                    paddings = ViewRect.Dp(
                        leftValue = 0,
                        topValue = 24,
                        rightValue = 0,
                        bottomValue = 0
                    )
                )
            )
        )
    }

    private fun mapCategoryKindState(
        model: CategoryModel?,
    ): CategoryKindItem.State {
        return CategoryKindItem.State(
            provideId = "category_kind_item_id",
            icon = model?.type?.let(categoryTypeMapper::getImageResId)?.let(ImageValue::Coil),
            color = ColorValue.parseColor(model?.colorName),
        )
    }

    private fun getAmount(
        sum: BigDecimal,
        isExpense: Boolean,
        currencySymbol: String?
    ): CharSequence {
        val text = sum
            .toString()
            .getFormatCurrency()

        val colorRes = if (isExpense) {
            baseR.color.red_600
        } else {
            baseR.color.green_600
        }

        return buildSpannedString {
            foreground(resManager.getColor(colorRes)) {
                if (isExpense) {
                    append("-")
                }
                append(text.first)
                currencySymbol?.let(::append)
            }
        }
    }

    private fun calculateCategoryPercentages(data: Map<CategoryModel, List<EventModel>>): Map<CategoryModel, Pair<BigDecimal, Int>> {
        val categorySums = data.mapValues { (_, events) ->
            events.sumOf { it.amountModel.value }
        }

        val totalSum = categorySums.values.sumOf { it }

        return if (totalSum == BigDecimal.ZERO) {
            data.keys.associateWith { BigDecimal.ZERO to 0 }
        } else {
            categorySums.mapValues { (_, sum) ->
                val percentage = (sum.toDouble() / totalSum.toDouble() * 100)
                sum to roundProperly(percentage)
            }
        }
    }

    private fun roundProperly(value: Double): Int {
        return if (value - value.toInt() >= 0.5) {
            value.toInt() + 1
        } else {
            value.toInt()
        }
    }

    private fun getStatisticState(
        isExpense: Boolean,
        percentMap: Map<CategoryModel, Pair<BigDecimal, Int>>
    ): StatisticItem.State {
        return StatisticItem.State(
            provideId = "report_statistic_details_id${isExpense}",
            cells = percentMap.map {
                StatisticItem.Cell(
                    color = ColorValue.parseColor(it.key.colorName),
                    percent = it.value.second
                )
            }
        )
    }

    private fun getHeaderState(
        id: String,
        text: String
    ): HeaderItem.State {
        return HeaderItem.State(
            provideId = id,
            title = text
        )
    }

    fun getReports(
        isExpense: Boolean,
        list: List<EventModel>
    ): ReportListModel {
        return ReportListModel(
            reports = list.map {
                ReportModel(
                    name = categoryNameMapper.getName(it.categoryModel?.name),
                    date = it.dateModel.value.yyyy_MM_dd(),
                    amount = it.amountModel.format,
                    type = it.budgetType.name,
                    description = it.description
                )
            },
            budgetType = if (isExpense) {
                BudgetType.EXPENSE
            } else {
                BudgetType.INCOME
            },
            sum = list
                .sumOf { it.amountModel.value }.toString()
                .getFormatCurrency()
                .first
        )
    }

    fun getCalendarText(date: LocalDateFormatter): String {
        return date.formatDateToLocalizedMonthYear()
    }

    private fun getEmptyText(): String {
        return resManager.getString(R.string.report_items_empty)
    }

    fun getErrorText(): String {
        return resManager.getString(R.string.report_global_error)
    }
}