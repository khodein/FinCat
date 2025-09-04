package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class UserLanguageItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val name: String,
        val icon: ImageValue,
        val isChecked: Boolean = false,
        val data: Any? = null,
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState

    data class Container(
        val padding: ViewRect.Dp = ZERO
    )
}