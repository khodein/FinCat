package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.FragmentCategoryEditorBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CategoryEditorFragment : Fragment(R.layout.fragment_category_editor) {
    private val viewModel by viewModel<CategoryEditorViewModel>()
    private val binding by viewBinding(init = FragmentCategoryEditorBinding::bind)
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setAdapter() {
        binding.categoryEditorList.itemAnimator = null
        binding.categoryEditorList.adapter = adapter
    }

    private fun setObservable() = with(viewModel) {
        topFlow
            .filterNotNull()
            .observe(
                this@CategoryEditorFragment,
                binding.categoryEditorTb::bindState
            )

        itemsFlow
            .observe(
                this@CategoryEditorFragment,
                adapter::submitList
            )

        buttonFlow
            .filterNotNull()
            .observe(this@CategoryEditorFragment) { state ->
                binding.categoryEditorBtn.run {
                    bindState(state)

                    doOnLayout {
                        binding.categoryEditorList.applyPadding(bottom = it.height)
                    }
                }
            }
    }
}