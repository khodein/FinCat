package com.android.pokhodai.expensemanagement.ui.report

import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment: BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }
}