package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository.FaqRepository
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.FaqModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.mapper.FaqMapper
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FaqViewModel(
    private val faqRepository: FaqRepository,
    private val faqMapper: FaqMapper,
    private val router: Router,
) : ViewModel() {

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    init {
        updateTop()
        fetchData()
    }

    private fun fetchData() {
        updateSuccess(faqRepository.getFaqList())
    }

    private fun updateSuccess(list: List<FaqModel>) {
        _itemsFlow.value = faqMapper.getItems(
            list = list
        )
    }

    private fun updateTop() {
        _topFlow.value = faqMapper.getToolbarItemState(::onClickBack)
    }

    private fun onClickBack() {
        router.pop()
    }
}