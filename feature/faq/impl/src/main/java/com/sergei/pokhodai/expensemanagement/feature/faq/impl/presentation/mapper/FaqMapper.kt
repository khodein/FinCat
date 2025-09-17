package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.mapper

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.R
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.FaqModel
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem

internal class FaqMapper(
    private val resManager: ResManager
) {

    fun getItems(
        list: List<FaqModel>
    ): List<RecyclerState> {
        return listOf()
    }

    fun getToolbarItemState(
        onClickBack: () -> Unit
    ): ToolbarItem.State {
        return ToolbarItem.State(
            title = ToolbarItem.Title(
                value = resManager.getString(R.string.faq_top_title)
            ),
            onClickNavigation = onClickBack
        )
    }
}