package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBusKeys
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.model.CategoryIconModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.mapper.CategoryIconMapper
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CategoryIconViewModel(
    private val router: Router,
    private val eventBus: EventBus,
    private val categoryIconMapper: CategoryIconMapper,
) : ViewModel() {
    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<String?>(null)
    val topFlow = _topFlow.asStateFlow()

    init {
        updateTop()
        updateItems()
    }

    private fun updateTop() {
        _topFlow.value = categoryIconMapper.getTitle()
    }

    private fun updateItems() {
        _itemsFlow.value = categoryIconMapper.getItems(::onClickIcon)
    }

    private fun onClickIcon(state: CategoryKindItem.State) {
        val data = state.data
        if (data is CategoryIconModel) {
            eventBus.push(data, EventBusKeys.CATEGORY_ICON)
        }
        router.pop()
    }
}