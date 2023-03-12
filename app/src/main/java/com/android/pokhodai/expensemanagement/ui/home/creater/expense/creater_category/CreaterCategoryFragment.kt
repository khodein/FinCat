package com.android.pokhodai.expensemanagement.ui.home.creater.expense.creater_category

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentCreaterCategoryBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.expense.creater_category.icons.IconsDialog
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreaterCategoryFragment :
    BaseFragment<FragmentCreaterCategoryBinding>(FragmentCreaterCategoryBinding::inflate) {

    private val viewModel by viewModels<CreaterCategoryViewModel>()

    override val isBnvVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
    }

    private fun setTitle() = with(binding) {
        val title = getString(if (viewModel.editOrCreateType == Creater.CREATE) {
            R.string.creater_category_title_add
        } else {
            R.string.creater_category_title_edit
        })
        tbCreaterCategory.title = title
        btnCreaterCategory.text = title
    }

    override fun setListeners() = with(binding) {
        txtCategoryNameCreaterCategory.doAfterTextChanged {
            viewModel.onChangeCategoryName(it.toString())
        }

        txtCategoryNameCreaterCategory.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (btnCreaterCategory.isEnabled) {
                    viewModel.onClickCreater()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        tbCreaterCategory.setNavigationOnClickListener {
            navigationController.popBackStack()
        }

        ivCreaterCategory.setOnClickListener {
            navigationController.navigateSafe(
                CreaterCategoryFragmentDirections.actionCreaterCategoryFragmentToIconsDialog()
            )
        }

        btnCreaterCategory.setOnClickListener {
            windowInsetsController.hide(WindowInsetsCompat.Type.ime())
            viewModel.onClickCreater()
        }

        setFragmentResultListener(IconsDialog.CHOOSE_ICON) { _, bundle ->
            bundle.getParcelable<Icons>(IconsDialog.ICON)?.let {
                viewModel.onChangeIcon(it)
            }
        }
    }

    override fun setObservable() = with(viewModel) {
        iconResIdFlow.observe(viewLifecycleOwner) {
            binding.ivCreaterCategory.setImageResource(it.resId)
        }

        validate.observe(viewLifecycleOwner) {
            binding.btnCreaterCategory.isEnabled = it
        }

        navigatePopFlow.observe(viewLifecycleOwner) {
            setFragmentResult(UPDATE_CATEGORY_SUCCESS, bundleOf())
            navigationController.popBackStack()
        }

        shackBarFlow.observe(viewLifecycleOwner) {
            showSnackBar(getString(R.string.creater_category_name), anchorView = binding.root)
        }

        updateFlow.observe(viewLifecycleOwner) {
            binding.run {
                txtCategoryNameCreaterCategory.setText(viewModel.editExpense?.name)
                ivCreaterCategory.setImageResource(viewModel.editExpense?.icon?.resId ?: R.drawable.ic_add_new_category)
            }
        }
    }

    companion object {
        const val UPDATE_CATEGORY_SUCCESS = "UPDATE_CATEGORY_SUCCESS"
     }
}