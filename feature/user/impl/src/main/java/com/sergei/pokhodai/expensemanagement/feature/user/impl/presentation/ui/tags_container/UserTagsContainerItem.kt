package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container

import com.sergei.pokhodai.expensemanagement.core.base.dimension.ViewRect
import com.sergei.pokhodai.expensemanagement.core.base.utils.ZERO
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItem

internal class UserTagsContainerItem {

    data class State(
        override val provideId: String,
        val container: Container = Container(),
        val tags: List<TagItem.State>,
    ) : RecyclerState

    data class Container(
        val paddings: ViewRect.Dp = ZERO
    )
}