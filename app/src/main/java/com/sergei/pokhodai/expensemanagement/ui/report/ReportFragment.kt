package com.sergei.pokhodai.expensemanagement.ui.report

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.ui.date_picker.MonthPickerDialog
import com.sergei.pokhodai.expensemanagement.ui.report.adapter.ReportAdapter
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.FragmentReportBinding
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.utils.getMonthLocalization
import com.sergei.pokhodai.expensemanagement.utils.navigateSafe
import com.sergei.pokhodai.expensemanagement.utils.observe
import com.sergei.pokhodai.expensemanagement.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    private val viewModel by viewModels<ReportViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var adapter: ReportAdapter

    @Inject
    lateinit var languageRepository: LanguageRepository

    override fun onBackPressed() {
        mainViewModel.onClickCountToExit()
    }

    override fun setAdapter() = with(binding) {
        rvReport.adapter = adapter
        rvReport.itemAnimator = null
    }

    override fun setListeners() {
        with(binding) {
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

        with(mainViewModel) {
            exitHelperFlow.observe(viewLifecycleOwner) {
                showSnackBar(getString(R.string.report_exit), binding.root)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.incMonthSelectorReport.run {
                chipDateMonthSelector.text =
                    "${it.getMonthLocalization(requireContext())} ${it.yyyy(languageRepository.getLanguage())}"
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