package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.DialogCategoryBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import com.sergei.pokhodai.expensemanagement.uikit.request.RequestItem
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CategoryDialog : BaseBottomSheetDialogFragment(R.layout.dialog_category) {
    private val binding by viewBinding(init = DialogCategoryBinding::bind)
    private var adapter: RecyclerAdapter? = null
    private val viewModel by viewModel<CategoryDialogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
        viewModel.onStart()
    }

    private fun setAdapter() {
        this.adapter = RecyclerAdapter()
        binding.categoryDialogList.adapter = adapter
        binding.categoryDialogList.itemAnimator = null
    }

    private fun setObservable() = with(viewModel) {
        bottomFlow
            .observe(this@CategoryDialog) { state ->
                binding.categoryDialogCreate.bindStateOptional(
                    state = state,
                    binder = ButtonItemView::bindState
                )
                if (state == null) {
                    binding.categoryDialogContainer.applyPadding(bottom = 0)
                    binding.categoryDialogRequest.applyPadding(bottom = 0)
                } else {
                    binding.categoryDialogCreate.doOnLayout {
                        binding.categoryDialogContainer.applyPadding(bottom = it.height)
                        binding.categoryDialogRequest.applyPadding(bottom = it.height)
                    }
                }
            }

        requestFlow.observe(this@CategoryDialog) { state ->
            binding.categoryDialogRequest.isVisible = state !is RequestItem.State.Idle
            binding.categoryDialogRequest.bindState(state)
        }

        topFlow
            .filterNotNull()
            .observe(this@CategoryDialog) { text ->
                binding.categoryDialogTitle.text = text
                binding.categoryDialogTitle.doOnLayout {
                    binding.categoryDialogRequest.applyPadding(top = it.height)
                }
            }

        itemsFlow.observe(this@CategoryDialog) { list ->
            adapter?.submitList(list)
        }
    }
}