package com.android.pokhodai.expensemanagement.ui.report

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentReportBinding
import com.android.pokhodai.expensemanagement.ui.home.HomeFragment
import com.android.pokhodai.expensemanagement.ui.home.date_picker.MonthPickerDialog
import com.android.pokhodai.expensemanagement.ui.report.adapter.ReportAdapter
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    private val viewModel by viewModels<ReportViewModel>()

    private val adapter by lazy { ReportAdapter() }

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }

    override fun setAdapter() = with(binding) {
        rvReport.adapter = adapter
        rvReport.itemAnimator = null
    }

    override fun setListeners() = with(binding) {
        srlReport.setOnRefreshListener {
            viewModel.onSwipeRefresh()
        }

        incMonthSelectorReport.run {
            btnLeftMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(MINUS)
            }

            btnRightMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(PLUS)
            }
        }
    }

    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.incMonthSelectorReport.run {
                chipDateMonthSelector.text = it.MMMM_yyyy()
            }
        }

        refreshFlow.observe(viewLifecycleOwner) {
            binding.srlReport.isRefreshing = it
        }

        statisticsFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        walletTypesFlow.observe(viewLifecycleOwner) {
            binding.txtOverviewReport.isVisible = it.isNotEmpty()
            binding.sovReport.isVisible = it.isNotEmpty()
            binding.sovReport.reportWallets = it
        }
    }

    companion object {
        const val PLUS = "PLUS"
        const val MINUS = "MINUS"
        private const val MONTH_PICKER_TAG = "MONTH_PICKER"
    }

}