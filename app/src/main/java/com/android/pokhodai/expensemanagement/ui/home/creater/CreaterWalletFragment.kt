package com.android.pokhodai.expensemanagement.ui.home.creater

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentCreaterWalletBinding
import com.android.pokhodai.expensemanagement.source.UserDataSource
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.ui.home.creater.expense.creater_category.CreaterCategoryFragment
import com.android.pokhodai.expensemanagement.ui.home.creater.income.IncomeDialog
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.masks.CreaterWalletMask
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.showDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreaterWalletFragment :
    BaseFragment<FragmentCreaterWalletBinding>(FragmentCreaterWalletBinding::inflate) {

    override val isBnvVisible: Boolean = false

    private val viewModel by viewModels<CreaterWalletViewModel>()

    @Inject
    lateinit var createrWalletMask: CreaterWalletMask

    override fun initToolbar() {
        binding.tbCreaterWallet.title = if (viewModel.editOrCreateType == Creater.CREATE) {
            getString(R.string.add_new_wallet_title_add_new)
        } else {
            getString(R.string.add_new_wallet_title_edit)
        }
    }

    override fun setListeners() = with(binding) {
        tbCreaterWallet.setNavigationOnClickListener {
            onBackPressed()
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
        windowInsetsController.hide(WindowInsetsCompat.Type.ime())
        
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

        typesWalletFlow.observe(viewLifecycleOwner) {
            binding.txtTypeCreaterWallet.setAdapter(
                ArrayAdapter.createFromResource(
                    requireActivity(),
                    it,
                    R.layout.item_spin_wallet
                )
            )
        }

        typeFlow.observe(viewLifecycleOwner) {
            if (it == viewModel.income) {
                binding.txtAmountCreaterWallet.removeTextChangedListener(createrWalletMask)
            } else {
                binding.txtAmountCreaterWallet.addTextChangedListener(createrWalletMask)
            }

            val (addOrEditIncome, addOrEditExpense) =
                if (viewModel.editOrCreateType == Creater.CREATE) {
                    binding.txtAmountCreaterWallet.setText("")
                    Pair(
                        getString(R.string.add_new_wallet_btn_income),
                        getString(R.string.add_new_wallet_btn_expense)
                    )
                } else {
                    Pair(
                        getString(R.string.add_new_wallet_btn_edit_income),
                        getString(R.string.add_new_wallet_btn_edit_expense)
                    )
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
            onBackPressed()
        }

        setFragmentResultListener(IncomeDialog.NEW_CATEGORY_RESULT) { _, bundle ->
            bundle.getParcelable<CategoriesAdapter.Categories>(IncomeDialog.NEW_CATEGORY)?.let {
                viewModel.onChangeCategoryName(it)
                binding.ivCreaterWallet.setImageResource(it.icon.resId)
                binding.ivCreaterWallet.isVisible = true
            }

            arguments?.remove(IncomeDialog.NEW_CATEGORY)
        }

        setFragmentResultListener(CreaterCategoryFragment.UPDATE_CATEGORY_SUCCESS) { _, _ ->
            onOpenIncomeOrExpenseDialog()
            clearFragmentResult(CreaterCategoryFragment.UPDATE_CATEGORY_SUCCESS)
        }
    }

    override fun onDestroyView() {
        binding.txtAmountCreaterWallet.removeTextChangedListener(createrWalletMask)
        super.onDestroyView()
    }

    companion object {
        const val ADD_NEW_WALLET = "ADD_NEW_WALLET"
    }
}