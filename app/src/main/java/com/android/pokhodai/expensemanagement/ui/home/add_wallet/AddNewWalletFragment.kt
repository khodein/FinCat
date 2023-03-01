package com.android.pokhodai.expensemanagement.ui.home.add_wallet

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentAddNewWalletBinding
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category.AddNewCategoryFragment
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.income.IncomeDialog
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.showDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewWalletFragment :
    BaseFragment<FragmentAddNewWalletBinding>(FragmentAddNewWalletBinding::inflate) {

    override val isBnvVisible: Boolean = false

    private val viewModel by viewModels<AddNewWalletViewModel>()

    override fun setListeners() = with(binding) {
        tbAddNewWallet.setNavigationOnClickListener {
            navigationController.popBackStack()
        }

        txtAmountAddNewWallet.doAfterTextChanged {
            viewModel.onChangeAmount(it.toString())
        }

        txtDescriptionAddNewWallet.doAfterTextChanged {
            viewModel.onChangeDescription(it.toString())
        }

        txtCategoryNameAddNewWallet.setOnThrottleClickListener {
            onOpenIncomeOrExpenseDialog()
        }

        txtDateNameAddNewWallet.setOnThrottleClickListener {
            childFragmentManager.showDatePickerDialog(
                viewModel.dateFlow.value
            ) {
                viewModel.onChangeDate(it)
            }
        }

        btnAddNewWallet.setOnClickListener {
            viewModel.onAddNewWallet()
        }

        txtTypeAddNewWallet.setOnItemClickListener { adapterView, _, i, _ ->
            viewModel.onChangeWalletType(adapterView.getItemAtPosition(i).toString())
        }
    }

    private fun onOpenIncomeOrExpenseDialog() {
        val navigateTo = if (viewModel.walletTypeFlow.value == viewModel.incomeText) {
            AddNewWalletFragmentDirections.actionAddNewWalletFragmentToIncomeDialog()
        } else {
            AddNewWalletFragmentDirections.actionAddNewWalletFragmentToExpenseDialog()
        }

        navigationController.navigateSafe(navigateTo)
    }

    override fun setObservable() = with(viewModel) {
        categoryNameFlow.observe(viewLifecycleOwner) {
            binding.txtCategoryNameAddNewWallet.setText(it.name)
        }

        walletTypeFlow.observe(viewLifecycleOwner) {
            val textBtn = if (it == viewModel.incomeText) {
                getString(R.string.add_new_wallet_btn_income)
            } else {
                getString(R.string.add_new_wallet_btn_expense)
            }
            binding.txtCategoryNameAddNewWallet.setText("")
            binding.ivAddNewCategory.isVisible = false
            binding.btnAddNewWallet.text = textBtn
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnAddNewWallet.isEnabled = it
        }

        dateFlow.observe(viewLifecycleOwner) {
            binding.txtDateNameAddNewWallet.setText(it.dd_MMMM_yyyy())
        }

        typesWallet.observe(viewLifecycleOwner) {
            binding.txtTypeAddNewWallet.setAdapter(
                ArrayAdapter(
                    this@AddNewWalletFragment.requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it
                )
            )
        }

        navigatePopFlow.observe(viewLifecycleOwner) {
            navigationController.popBackStack()
        }

        setFragmentResultListener(IncomeDialog.NEW_CATEGORY_RESULT) { _, bundle ->
            bundle.getParcelable<CategoriesAdapter.Categories>(IncomeDialog.NEW_CATEGORY)?.let {
                viewModel.onChangeCategoryName(it)
                binding.ivAddNewCategory.setImageResource(it.icon.resId)
                binding.ivAddNewCategory.isVisible = true
            }

            arguments?.remove(IncomeDialog.NEW_CATEGORY)
        }

        setFragmentResultListener(AddNewCategoryFragment.NEW_CATEGORY_SUCCESS) { key, bundle ->
            onOpenIncomeOrExpenseDialog()
        }
    }
}