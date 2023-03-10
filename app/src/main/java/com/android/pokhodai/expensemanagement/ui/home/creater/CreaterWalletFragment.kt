package com.android.pokhodai.expensemanagement.ui.home.creater

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentCreaterWalletBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.ui.home.creater.expense.add_new_category.AddNewCategoryFragment
import com.android.pokhodai.expensemanagement.ui.home.creater.income.IncomeDialog
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.showDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreaterWalletFragment :
    BaseFragment<FragmentCreaterWalletBinding>(FragmentCreaterWalletBinding::inflate) {

    override val isBnvVisible: Boolean = false

    private val viewModel by viewModels<CreaterWalletViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.editOrCreateType == Creater.CREATE) {
            binding.tbCreaterWallet.title = getString(R.string.add_new_wallet_title_add_new)
        } else {
            binding.tbCreaterWallet.title = getString(R.string.add_new_wallet_title_edit)
        }
    }

    override fun setListeners() = with(binding) {
        tbCreaterWallet.setNavigationOnClickListener {
            navigationController.popBackStack()
        }

        txtAmountCreaterWallet.doAfterTextChanged {
            viewModel.onChangeAmount(it.toString())
        }

        txtDescriptionCreaterWallet.doAfterTextChanged {
            viewModel.onChangeDescription(it.toString())
        }

        txtCategoryNameCreaterWallet.setOnThrottleClickListener {
            onOpenIncomeOrExpenseDialog()
        }

        txtDateNameCreaterWallet.setOnThrottleClickListener {
            childFragmentManager.showDatePickerDialog(
                viewModel.dateFlow.value
            ) {
                viewModel.onChangeDate(it)
            }
        }

        btnCreaterWallet.setOnClickListener {
            viewModel.onAddNewWallet()
        }

        txtTypeCreaterWallet.setOnItemClickListener { adapterView, _, i, _ ->
            viewModel.onChangeWalletType(adapterView.getItemAtPosition(i).toString())
        }
    }

    private fun onOpenIncomeOrExpenseDialog() {
        val navigateTo = if (viewModel.typeFlow.value == viewModel.income) {
            CreaterWalletFragmentDirections.actionCreaterWalletFragmentToIncomeDialog()
        } else {
            CreaterWalletFragmentDirections.actionCreaterWalletFragmentToExpenseDialog()
        }

        navigationController.navigateSafe(navigateTo)
    }

    override fun setObservable() = with(viewModel) {
        categoryNameFlow.observe(viewLifecycleOwner) {
            binding.txtCategoryNameCreaterWallet.setText(it.name)
        }

        typeFlow.observe(viewLifecycleOwner) {
            val addOrEditIncome = if (viewModel.editOrCreateType == Creater.CREATE) {
                getString(R.string.add_new_wallet_btn_income)
            } else {
                getString(R.string.add_new_wallet_btn_edit_income)
            }

            val addOrEditExpense = if (viewModel.editOrCreateType == Creater.CREATE) {
                getString(R.string.add_new_wallet_btn_expense)
            } else {
                getString(R.string.add_new_wallet_btn_edit_expense)
            }

            val textBtn = if (it == viewModel.income) {
                addOrEditIncome
            } else {
                addOrEditExpense
            }

            binding.txtCategoryNameCreaterWallet.setText("")
            binding.ivCreaterWallet.isVisible = false
            binding.btnCreaterWallet.text = textBtn
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnCreaterWallet.isEnabled = it
        }

        dateFlow.observe(viewLifecycleOwner) {
            binding.txtDateNameCreaterWallet.setText(it.dd_MMMM_yyyy())
        }

        typesWallet.observe(viewLifecycleOwner) {
            binding.txtTypeCreaterWallet.setAdapter(
                ArrayAdapter(
                    this@CreaterWalletFragment.requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it
                )
            )
        }

        editWalletFlow.observe(viewLifecycleOwner) {
            binding.run {
                ivCreaterWallet.isVisible = true
                txtCategoryNameCreaterWallet.setText(categoryNameFlow.value.name)
                ivCreaterWallet.setImageResource(categoryNameFlow.value.icon.resId)
                txtDateNameCreaterWallet.setText(dateFlow.value.dd_MM_yyyy())
                txtAmountCreaterWallet.setText(amountFlow.value)
                txtDescriptionCreaterWallet.setText(descriptionFlow.value)
                txtTypeCreaterWallet.setText(typeFlow.value)
            }
        }

        navigatePopFlow.observe(viewLifecycleOwner) {
            setFragmentResult(ADD_NEW_WALLET, bundleOf())
            navigationController.popBackStack()
        }

        setFragmentResultListener(IncomeDialog.NEW_CATEGORY_RESULT) { _, bundle ->
            bundle.getParcelable<CategoriesAdapter.Categories>(IncomeDialog.NEW_CATEGORY)?.let {
                viewModel.onChangeCategoryName(it)
                binding.ivCreaterWallet.setImageResource(it.icon.resId)
                binding.ivCreaterWallet.isVisible = true
            }

            arguments?.remove(IncomeDialog.NEW_CATEGORY)
        }

        setFragmentResultListener(AddNewCategoryFragment.NEW_CATEGORY_SUCCESS) { key, bundle ->
            onOpenIncomeOrExpenseDialog()
        }
    }

    companion object {
        const val ADD_NEW_WALLET = "ADD_NEW_WALLET"
    }
}