package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.sergei.pokhodai.expensemanagement.core.eventbus.api.EventBus
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.model.UserAvatarModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.UserModule
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper.UserAvatarMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list.UserAvatarListItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.contract.UserAvatarContract
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserAvatarViewModel(
    private val userAvatarMapper: UserAvatarMapper,
    private val eventBus: EventBus,
    private val router: Router,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<UserAvatarContract>()
    private var avatar = route.avatar?.let(UserAvatarModel::valueOf)
    private val previousAvatar = avatar

    private val _itemsFlow = MutableStateFlow<List<RecyclerState>>(emptyList())
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _topFlow = MutableStateFlow<String?>(null)
    val topFlow = _topFlow.asStateFlow()

    private val _bottomFlow = MutableStateFlow<ButtonItem.State?>(null)
    val bottomFlow = _bottomFlow.asStateFlow()

    init {
        updateTop()
        updateBottom()
        updateItems()
    }

    private fun updateItems() {
        _itemsFlow.value = userAvatarMapper.getItems(
            avatar = avatar,
            list = UserAvatarModel.entries,
            onClick = ::onClickAvatar
        )
    }

    private fun updateTop() {
        _topFlow.value = userAvatarMapper.getTitleText()
    }

    private fun updateBottom() {
        _bottomFlow.value = userAvatarMapper.getButtonItemState(::onClickButton)
    }

    private fun onClickAvatar(state: UserAvatarListItem.State) {
        val data = state.data
        if (data is UserAvatarModel) {
            avatar = data
            updateItems()
        }
    }

    private fun onClickButton(state: ButtonItem.State) {
        val avatar = this.avatar
        if (previousAvatar != avatar && avatar != null) {
            eventBus.push(avatar,UserModule.Keys.USER_AVATAR)
        }
       router.pop()
    }
}