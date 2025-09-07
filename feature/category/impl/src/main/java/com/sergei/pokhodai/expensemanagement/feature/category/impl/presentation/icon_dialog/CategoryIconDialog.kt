package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog

import android.os.Bundle
import android.view.View
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.DialogCategoryIconBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CategoryIconDialog : BaseBottomSheetDialogFragment(R.layout.dialog_category_icon) {

    private val binding by viewBinding(init = DialogCategoryIconBinding::bind)
    private val viewModel by viewModel<CategoryIconViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@CategoryIconDialog) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(
                this@CategoryIconDialog,
                binding.categoryIconDialogTitle::setText
            )
    }

    private fun setAdapter() {
        this.adapter = RecyclerAdapter()
        binding.categoryIconDialogList.itemAnimator = null
        binding.categoryIconDialogList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}