package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.category_editor

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
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.ViewCategoryEditorItemBinding
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItem
import com.sergei.pokhodai.expensemanagement.uikit.kind.CategoryKindItemView

internal class CategoryEditorItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), RecyclerItemView<CategoryEditorItem.State> {
    private var state: CategoryEditorItem.State? = null

    private val binding = ViewCategoryEditorItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT,
        )

        binding.viewCategoryEditorLeadingItem.load(ImageValue.Coil(R.drawable.ic_add_new_category))

        binding.viewCategoryEditorLeadingItem.setOnDebounceClick {
            this.state?.onClickLeading?.invoke()
        }
    }

    private fun onClickKind(state: CategoryKindItem.State) {
        this.state?.onClickLeading?.invoke()
    }

    override fun bindState(state: CategoryEditorItem.State) {
        this.state = state
        applyPadding(state.paddings)
        binding.viewCategoryEditorSelectItem.bindState(state.textFieldItemState)
        binding.viewCategoryEditorKindItem.bindStateOptional(
            state = state.kindItemState?.copy(onClick = ::onClickKind),
            binder = CategoryKindItemView::bindState
        )
        binding.viewCategoryEditorLeadingItem.isVisible = state.kindItemState == null
    }
}