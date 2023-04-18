package com.sergei.pokhodai.expensemanagement.ui.date_picker

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.databinding.DialogMonthPickerBinding
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthPickerDialog :
    BaseBottomSheetDialogFragment<DialogMonthPickerBinding>(DialogMonthPickerBinding::inflate) {

    private val args by navArgs<MonthPickerDialogArgs>()

    override fun setListeners() = with(binding) {
        mpvDatePicker.setOnClickMonthActionListener {
            setFragmentResult(CHANGE_DATE, bundleOf(DATE to it.timeInMillis()))
            dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mpvDatePicker.actualMonth = LocalDateFormatter.from(args.date)
    }

    companion object {
        const val CHANGE_DATE = "CHANGE_DATE"
        const val DATE = "DATE"
    }
}