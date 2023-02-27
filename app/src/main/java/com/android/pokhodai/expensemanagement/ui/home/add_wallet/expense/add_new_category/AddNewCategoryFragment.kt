package com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category

import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentAddNewCategoryBinding
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category.icons.IconsDialog
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCategoryFragment :
    BaseFragment<FragmentAddNewCategoryBinding>(FragmentAddNewCategoryBinding::inflate) {

    private val viewModel by viewModels<AddNewCategoryViewModel>()

    override val isBnvVisible: Boolean = false

    override fun setListeners() = with(binding) {
        txtCategoryNameAddNewCategory.doAfterTextChanged {
            viewModel.onChangeCategoryName(it.toString())
        }

        txtCategoryNameAddNewCategory.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (btnAddNewCategory.isEnabled) {
                    viewModel.onAddNewCategory()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        tbAddNewCategory.setNavigationOnClickListener {
            navigationController.popBackStack()
        }

        ivAddNewCategory.setOnClickListener {
            navigationController.navigateSafe(
                AddNewCategoryFragmentDirections.actionAddNewCategoryFragmentToIconsDialog()
            )
        }

        btnAddNewCategory.setOnClickListener {
            windowInsetsController.hide(WindowInsetsCompat.Type.ime())
            viewModel.onAddNewCategory()
        }

        setFragmentResultListener(IconsDialog.CHOOSE_ICON) { _, bundle ->
            bundle.getInt(IconsDialog.ICON).let {
                viewModel.onChangeIcon(it)
            }
        }
    }

    override fun setObservable() = with(viewModel) {
        iconResIdFlow.observe(viewLifecycleOwner) {
            binding.ivAddNewCategory.setImageResource(it)
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnAddNewCategory.isEnabled = it
        }

        navigatePopFlow.observe(viewLifecycleOwner) {
            setFragmentResult(NEW_CATEGORY_SUCCESS, bundleOf())
            navigationController.popBackStack()
        }
    }

    companion object {
        const val NEW_CATEGORY_SUCCESS = "NEW_CATEGORY_SUCCESS"
     }
}