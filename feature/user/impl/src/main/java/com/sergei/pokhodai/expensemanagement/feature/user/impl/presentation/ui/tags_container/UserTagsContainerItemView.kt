package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.google.android.material.chip.ChipGroup
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.dp
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.uikit.tag.TagItemView

internal class UserTagsContainerItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr), RecyclerItemView<UserTagsContainerItem.State> {

    private val tagMap = hashMapOf<String, TagItemView>()

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        chipSpacingVertical = 8.dp
        chipSpacingHorizontal = 8.dp
    }

    override fun bindState(state: UserTagsContainerItem.State) {
        applyPadding(state.container.paddings)
        removeAllViews()
        state.tags.forEach { state ->
            val view = tagMap.getOrPut(state.provideId) {
                TagItemView(context)
            }
            view.bindState(state)
            addView(view)
        }
    }
}