package com.android.pokhodai.expensemanagement.ui.home.date_picker

import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.android.pokhodai.expensemanagement.databinding.DialogMonthPickerBinding
import com.android.pokhodai.expensemanagement.ui.home.HomeViewModel
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthPickerDialog :
    BaseBottomSheetDialogFragment<DialogMonthPickerBinding>(DialogMonthPickerBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>({ requireParentFragment() })

    override fun setListeners() = with(binding) {
        mpvDatePicker.setOnClickMonthActionListener {
            viewModel.onChangeMonthDate(it)
            dismiss()
        }
    }

    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.mpvDatePicker.actualMonth = it
        }
    }
}