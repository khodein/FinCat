package com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.presentation.ui.event_category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.image.ImageValue
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.load
import com.sergei.pokhodai.expensemanagement.core.base.utils.setOnDebounceClick
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerItemView
import com.sergei.pokhodai.expensemanagement.core.uikit.R
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.databinding.ViewEventEditorCategoryItemBinding
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItemView

internal class EventEditorCategoryItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<EventEditorCategoryItem.State> {
    private var state: EventEditorCategoryItem.State? = null
    private val binding = ViewEventEditorCategoryItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        binding.viewCategoryLeadingItem.load(ImageValue.Res(R.drawable.ic_add_new_category))

        binding.viewCategoryLeadingItem.setOnDebounceClick {
            this.state?.onClick?.invoke()
        }
    }

    private fun onClickKind(state: CategoryKindItem.State) {
        this.state?.onClick?.invoke()
    }

    override fun bindState(state: EventEditorCategoryItem.State) {
        this.state = state
        applyPadding(state.paddings)
        binding.viewCategorySelectItem.bindState(state.selectItemState)
        binding.viewCategoryKindItem.bindStateOptional(
            state = state.kindItemState?.copy(onClick = ::onClickKind),
            binder = CategoryKindItemView::bindState
        )
        binding.viewCategoryLeadingItem.isVisible = state.kindItemState == null
    }
}