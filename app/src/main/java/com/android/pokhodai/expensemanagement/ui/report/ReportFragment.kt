package com.android.pokhodai.expensemanagement.ui.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentReportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment: BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    private val viewModel by viewModels<ReportViewModel>()

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}