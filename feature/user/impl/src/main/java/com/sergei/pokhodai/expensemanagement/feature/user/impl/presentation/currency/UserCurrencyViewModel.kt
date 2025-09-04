package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.base.utils.P_16_8_16_16
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserCurrencyModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.UserModule
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper.UserCurrencyMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserCurrencyContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserCurrencyViewModel(
    private val userCurrencyMapper: UserCurrencyMapper,
    private val eventBus: EventBus,
    private val router: Router,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<UserCurrencyContract>()
    private var currency = UserCurrencyModel.valueOf(route.currency)
    private val previousCurrency = currency
    private val _userTagFlow = MutableStateFlow<UserTagsContainerItem.State?>(null)
    val userTagFlow = _userTagFlow.asStateFlow()

    private val _titleFlow = MutableStateFlow<String?>(null)
    val titleFlow = _titleFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    init {
        updateTop()
        updateBottom()
        updateItems()
    }

    private fun updateTop() {
        _titleFlow.value = userCurrencyMapper.getTitle()
    }

    private fun updateItems() {
        _userTagFlow.value = UserTagsContainerItem.State(
            provideId = "user_currency_container",
            container = UserTagsContainerItem.Container(
                paddings = P_16_8_16_16
            ),
            tags = userCurrencyMapper.getTagItems(
                currency = currency,
                list = UserCurrencyModel.entries,
                onClickTag = ::onClickTag
            )
        )
    }

    private fun updateBottom() {
        _bottomFlow.value = userCurrencyMapper.getButtonItemState(::onClickButton)
    }

    private fun onClickTag(state: TagItem.State) {
        val data = state.data
        if (data is UserCurrencyModel) {
            currency = data
            updateItems()
        }
    }

    private fun onClickButton(state: ButtonItem.State) {
        if (previousCurrency != currency) {
            eventBus.push(currency, UserModule.Keys.USER_CURRENCY)
        }
        router.pop()
    }
}