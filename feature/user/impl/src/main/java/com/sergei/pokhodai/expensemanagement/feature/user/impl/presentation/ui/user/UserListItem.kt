package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class UserListItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val name: String,
        val email: String?,
        val currency: String?,
        val isChecked: Boolean = false,
        val art: ImageValue?,
        val data: Any? = null,
        val onClickDelete: ((state: State) -> Unit)? = null,
        val onClickEdit: ((state: State) -> Unit)? = null,
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO
    )
}