package com.android.pokhodai.expensemanagement.ui.report

import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentReportBinding
import com.android.pokhodai.expensemanagement.repositories.LanguageRepository
import com.android.pokhodai.expensemanagement.ui.date_picker.MonthPickerDialog
import com.android.pokhodai.expensemanagement.ui.report.adapter.ReportAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    private val viewModel by viewModels<ReportViewModel>()

    @Inject
    lateinit var adapter: ReportAdapter

    @Inject
    lateinit var languageRepository: LanguageRepository

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

            chipDateMonthSelector.setOnClickListener {
                navigationController.navigateSafe(
                    ReportFragmentDirections.actionReportFragmentToMonthPickerDialog(
                        viewModel.dateFlow.value.timeInMillis()
                    )
                )
            }

            btnRightMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(PLUS)
            }
        }

        setFragmentResultListener(MonthPickerDialog.CHANGE_DATE) { _, bundle ->
            val timeInMillis = bundle.getLong(MonthPickerDialog.DATE)
            viewModel.onChangeMonthDate(LocalDateFormatter.from(timeInMillis))
        }
    }

    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.incMonthSelectorReport.run {
                chipDateMonthSelector.text = it.MMMM_yyyy(languageRepository.getLanguage())
            }
        }

        refreshFlow.observe(viewLifecycleOwner) {
            binding.srlReport.isRefreshing = false
        }

        statisticsFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        walletTypesFlow.observe(viewLifecycleOwner) {
            binding.txtOverviewReport.isVisible = it.isNotEmpty()
            binding.sovReport.isVisible = it.isNotEmpty()
            binding.sovReport.reportWallets = it
        }

        navViewModel.refreshFlow.observe(viewLifecycleOwner) {
            onSwipeRefresh()
        }
    }

    companion object {
        const val PLUS = "PLUS"
        const val MINUS = "MINUS"
    }
}