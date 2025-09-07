package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list

import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class UserAvatarListItem {

    data class State(
        override val provideId: String,
        val isChecked: Boolean = false,
        val art: ImageValue,
        val data: Any? = null,
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState
}