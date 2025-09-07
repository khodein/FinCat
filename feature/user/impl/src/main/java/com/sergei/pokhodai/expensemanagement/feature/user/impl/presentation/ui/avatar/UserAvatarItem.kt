package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class UserAvatarItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val art: ImageValue,
        val editText: String,
        val onClickEdit: (() -> Unit)? = null
    ) : RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO
    )
}