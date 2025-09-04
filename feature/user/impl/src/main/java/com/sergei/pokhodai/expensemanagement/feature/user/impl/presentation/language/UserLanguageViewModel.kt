package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language

import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.support.api.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.api.model.LocaleLanguageModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.mapper.UserLanguageMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserLanguageViewModel(
    private val userLanguageMapper: UserLanguageMapper,
    private val localeManager: LocaleManager,
    private val router: Router,
) : ViewModel() {

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<String?>(null)
    val topFlow = _topFlow.asStateFlow()

    private var localeLanguageModel: LocaleLanguageModel = localeManager.getLanguage()
    private val previousLocaleLanguageModel = localeLanguageModel

    init {
        updateItems()
        updateTop()
        updateBottom()
    }

    private fun onClickLanguageItem(state: UserLanguageItem.State) {
        val data = state.data
        if (data is LocaleLanguageModel) {
            localeLanguageModel = data
            updateItems()
        }
    }

    private fun updateItems() {
        _itemsFlow.value = userLanguageMapper.getItems(
            localeLanguageModel = localeLanguageModel,
            list = LocaleLanguageModel.entries,
            onClick = ::onClickLanguageItem
        )
    }

    private fun updateTop() {
        _topFlow.value = userLanguageMapper.getTitleText()
    }

    private fun updateBottom() {
        _bottomFlow.value = userLanguageMapper.getButtonItemState(
            onClick = ::onClickButton
        )
    }

    private fun onClickButton(state: ButtonItem.State) {
        if (localeLanguageModel != previousLocaleLanguageModel) {
            localeManager.updateLanguage(localeLanguageModel)
        }
        router.pop()
    }
}