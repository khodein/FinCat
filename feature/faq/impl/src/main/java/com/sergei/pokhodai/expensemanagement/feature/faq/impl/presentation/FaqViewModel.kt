package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.faq.api.FaqRouter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.data.repository.FaqRepository
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.domain.model.FaqModel
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation.mapper.FaqMapper
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.toolbar.ToolbarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class FaqViewModel(
    private val faqRepository: FaqRepository,
    private val faqMapper: FaqMapper,
    private val router: Router,
    private val faqRouter: FaqRouter,
) : ViewModel() {

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<ToolbarItem.State?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    init {
        updateTop()
        updateBottom()
        updateItems()
    }

    private fun updateItems() {
        _itemsFlow.value = faqMapper.getItems(
            list = faqRepository.getFaqList(),
        )
    }

    private fun updateBottom() {
        _bottomFlow.value = faqMapper.getButtonQuestion(::onClickQuestion)
    }
    private fun updateTop() {
        _topFlow.value = faqMapper.getToolbarItemState(::onClickBack)
    }

    private fun onClickBack() {
        router.pop()
    }

    private fun onClickQuestion(state: ButtonItem.State) {
        faqRouter.goToQuestion()
    }
}