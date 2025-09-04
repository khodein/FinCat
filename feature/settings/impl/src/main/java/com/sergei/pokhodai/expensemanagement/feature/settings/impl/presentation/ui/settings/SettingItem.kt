package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.settings

import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class SettingItem {

    data class State(
        override val provideId: String,
        val leadingIcon: ImageValue,
        val trailingIcon: ImageValue? = null,
        val name: CharSequence,
        val data: Any? = null,
        val onClick: ((state: State) -> Unit)? = null
    ): RecyclerState
}