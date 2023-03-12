package com.android.pokhodai.expensemanagement.ui.home.creater.expense

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.android.pokhodai.expensemanagement.databinding.DialogCategoriesBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.ui.home.creater.income.IncomeDialog
import com.android.pokhodai.expensemanagement.utils.decorations.GridSpacingItemDecoration
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExpenseDialog :
    BaseBottomSheetDialogFragment<DialogCategoriesBinding>(DialogCategoriesBinding::inflate) {

    private val viewModel by viewModels<ExpenseViewModel>()

    @Inject
    lateinit var adapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddNewCategories.isVisible = true
    }

    override fun setAdapter() {
        binding.run {
            rvCategories.addItemDecoration(GridSpacingItemDecoration(3, 10, false))
            rvCategories.adapter = adapter
        }
    }

    override fun setObservable() = with(viewModel) {
        expenseFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.incEmptyCategories.run {
                root.isVisible = it.isEmpty()
                root.text = getString(R.string.expense_empty)
            }
        }
    }

    override fun setListeners() = with(binding) {
        btnAddNewCategories.setOnClickListener {
            navigationController.navigateSafe(
                ExpenseDialogDirections.actionCreaterWalletFragmentToCreaterCategoryNavGraph(Creater.CREATE)
            )
        }

        adapter.setOnClickCategoryActionListener {
            setFragmentResult(
                IncomeDialog.NEW_CATEGORY_RESULT,
                bundleOf(IncomeDialog.NEW_CATEGORY to it)
            )
            dismiss()
        }

        adapter.setOnLongClickActionListener {
            viewModel.onDeleteExpenseById(it)
        }
    }
}