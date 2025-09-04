package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.DialogCategoryBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CategoryDialog : BaseBottomSheetDialogFragment(R.layout.dialog_category) {
    private val binding by viewBinding(init = DialogCategoryBinding::bind)
    private val adapter by autoClean(init = ::RecyclerAdapter)
    private val viewModel by viewModel<CategoryDialogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setAdapter() {
        binding.categoryDialogList.adapter = adapter
        binding.categoryDialogList.itemAnimator = null
    }

    private fun setObservable() = with(viewModel) {
        bottomFlow
            .filterNotNull()
            .observe(this@CategoryDialog) { state ->
                binding.categoryDialogCreate.bindState(state)
                binding.categoryDialogCreate.doOnLayout {
                    binding.categoryDialogList.applyPadding(bottom = it.height)
                }
            }

        topFlow
            .filterNotNull()
            .observe(this@CategoryDialog) { text ->
                binding.categoryDialogTitle.text = text
                binding.categoryDialogTitle.doOnLayout {
                    binding.categoryDialogList.applyPadding(top = it.height)
                }
            }

        itemsFlow.observe(
            this@CategoryDialog,
            adapter::submitList
        )
    }
}