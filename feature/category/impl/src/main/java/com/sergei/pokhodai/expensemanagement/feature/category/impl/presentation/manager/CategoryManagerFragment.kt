package com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.R
import com.sergei.pokhodai.expensemanagement.feature.category.impl.databinding.FragmentManagerCategoryBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class CategoryManagerFragment : Fragment(R.layout.fragment_manager_category) {

    private val binding by viewBinding(init = FragmentManagerCategoryBinding::bind)
    private val viewModel by viewModel<CategoryManagerViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@CategoryManagerFragment) { list ->
            adapter?.submitList(list)
        }

        bottomFlow
            .observe(this@CategoryManagerFragment) { state ->
                binding.managerCategoryBottom.bindStateOptional(
                    state = state,
                    binder = ButtonItemView::bindState
                )
                if (state == null) {
                    binding.managerCategoryItems.updatePadding(bottom = 0)
                    binding.managerRequestItem.updatePadding(bottom = 0)
                } else {
                    binding.managerCategoryBottom.doOnLayout {
                        binding.managerCategoryItems.updatePadding(bottom = it.height)
                        binding.managerRequestItem.updatePadding(bottom = it.height)
                    }
                }
            }

        topFlow
            .filterNotNull()
            .observe(
                this@CategoryManagerFragment,
                binding.managerCategoryTb::bindState
            )

        requestFlow.observe(
            this@CategoryManagerFragment,
            binding.managerRequestItem::bindState
        )
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.managerCategoryItems.adapter = adapter
        binding.managerCategoryItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}