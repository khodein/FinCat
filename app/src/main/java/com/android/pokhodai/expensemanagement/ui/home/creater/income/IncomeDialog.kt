package com.android.pokhodai.expensemanagement.ui.home.creater.income

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.android.pokhodai.expensemanagement.databinding.DialogCategoriesBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.decorations.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncomeDialog :
    BaseBottomSheetDialogFragment<DialogCategoriesBinding>(DialogCategoriesBinding::inflate) {

    private val viewModel by viewModels<IncomeViewModel>()

    @Inject
    lateinit var adapter: CategoriesAdapter

    override fun setAdapter() = with(binding) {
        rvCategories.addItemDecoration(GridSpacingItemDecoration(3, 30, false))
        rvCategories.adapter = adapter
        adapter.submitList(viewModel.incomeCategoriesList)
    }

    override fun setListeners() {
        adapter.setOnClickCategoryActionListener {
            setFragmentResult(NEW_CATEGORY_RESULT, bundleOf(NEW_CATEGORY to it))
            dismiss()
        }
    }

    companion object {
        const val NEW_CATEGORY_RESULT = "NEW_CATEGORY_RESULT"
        const val NEW_CATEGORY = "NEW_CATEGORY"
    }
}