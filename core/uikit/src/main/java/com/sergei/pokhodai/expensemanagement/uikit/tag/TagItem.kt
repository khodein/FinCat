package com.sergei.pokhodai.expensemanagement.uikit.tag

import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class TagItem {

    data class State(
        override val provideId: String,
        val isEnabled: Boolean = true,
        val isActive: Boolean = false,
        val icon: ImageValue? = null,
        val value: String,
        val data: Any? = null,
        val onClick: ((state: State) -> Unit)? = null
    ): RecyclerState
}