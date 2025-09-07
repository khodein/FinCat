package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile

import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class SettingProfileItem {

    data class State(
        override val provideId: String,
        val name: String,
        val currency: String?,
        val art: ImageValue?,
        val data: Any? = null,
        val email: String?,
        val onClick: ((state: State) -> Unit)? = null
    ) : RecyclerState
}