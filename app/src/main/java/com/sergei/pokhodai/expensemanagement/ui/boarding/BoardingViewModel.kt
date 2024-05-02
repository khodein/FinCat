package com.sergei.pokhodai.expensemanagement.ui.boarding

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.resourse.ResourceManager
import com.sergei.pokhodai.expensemanagement.source.UserDataSource
import com.sergei.pokhodai.expensemanagement.ui.boarding.ui.BoardingItem
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItem
import com.sergei.pokhodai.expensemanagement.utils.ViewDimension
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    userDataSource: UserDataSource,
    resourceManager: ResourceManager,
): ViewModel() {

    private val _buttonItemStateFlow = MutableStateFlow<ButtonItem.State?>(null)
    val buttonItemStateFlow = _buttonItemStateFlow.asStateFlow()

    private val _boardingItemStateFlow = MutableStateFlow<BoardingItem.State?>(null)
    val boardingItemStateFlow = _boardingItemStateFlow.asStateFlow()

    init {
        _buttonItemStateFlow.value = ButtonItem.State(
            id = "boarding_button_item_id",
            text = resourceManager.getString(R.string.boarding_btn),
            containerPadding = DpRect(20.dp, 20.dp, 20.dp, 20.dp),
            size = ButtonItem.Size.DEFAULT.copy(width = ViewDimension.MatchParent)
        )

        _boardingItemStateFlow.value = BoardingItem.State(
            id = "boarding_item_id",
            title = resourceManager.getString(R.string.app_name),
            caption = resourceManager.getString(R.string.boarding_subtitle),
            imageRes = R.drawable.bgr_splash
        )

        userDataSource.isFirstEntry = true
    }
}