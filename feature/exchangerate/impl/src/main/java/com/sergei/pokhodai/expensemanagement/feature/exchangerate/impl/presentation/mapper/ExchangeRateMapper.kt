package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.api.domain.model.CbrDailyModel
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.R
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation.ui.exchange_rate.ExchangeRateItem
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlin.random.Random

internal class ExchangeRateMapper(
    private val resManager: ResManager,
) {
    fun getToolbarItemState(
        onClickBack: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.exchange_rate_title)
            ),
            onClickNavigation = onClickBack
        )
    }

    fun getRequestErrorState(
        onReload: () -> Unit
    ): RequestItem.State.Error {
        return RequestItem.State.Error(
            message = resManager.getString(R.string.exchange_rate_error),
            onClickReload = onReload
        )
    }

    fun getRequestEmptyState(): RequestItem.State.Empty {
        return RequestItem.State.Empty(
            message = resManager.getString(R.string.exchange_rate_empty)
        )
    }

    fun getItems(
        list: List<CbrDailyModel.ValuteModel>
    ): List<RecyclerState> {
        if (list.isEmpty()) {
            return emptyList()
        }

        return buildList {
            add(
                ExchangeRateItem.State(
                    provideId = "exchange_rate_header",
                    isHeader = true,
                    name = resManager.getString(R.string.exchange_rate_header_name),
                    nominal = resManager.getString(R.string.exchange_rate_header_nominal),
                    rate = resManager.getString(R.string.exchange_rate_header_rate)
                )
            )

            list.sortedBy { it.charCode.order }.forEach {
                add(
                    ExchangeRateItem.State(
                        provideId = Random.nextInt().toString(),
                        name = it.name,
                        nominal = it.nominal.toString(),
                        rate = it.value.toString()
                    )
                )
            }
        }
    }
}