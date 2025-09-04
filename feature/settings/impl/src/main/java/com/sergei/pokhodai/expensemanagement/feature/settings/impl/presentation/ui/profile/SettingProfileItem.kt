package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation.ui.profile

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

class SettingProfileItem {

    data class State(
        override val provideId: String,
        val name: String,
        val prefix: String,
        val email: String,
        val onClick: (() -> Unit)? = null
    ): RecyclerState
}