package com.android.pokhodai.expensemanagement.ui.home

import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentHomeBinding
import com.android.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.android.pokhodai.expensemanagement.ui.home.date_picker.MonthPickerDialog
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.observeLatest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy { WalletAdapter() }

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }

    override fun setAdapter() = with(binding) {
        rvHome.adapter = adapter
    }

    override fun setListeners() = with(binding) {
        btnAddNewHome.setOnClickListener {
            navigationController.navigateSafe(
                HomeFragmentDirections.actionHomeFragmentToAddNewWalletFragment()
            )
        }

        incMonthSelectorHome.run {
            btnLeftMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(MINUS)
            }

            chipDateMonthSelector.setOnThrottleClickListener {
                MonthPickerDialog().show(childFragmentManager, MONTH_PICKER_TAG)
            }

            btnRightMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(PLUS)
            }
        }

        adapter.setOnLongClickActionListener {
            viewModel.onClickDeleteWallet(it)
        }
    }

    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.incMonthSelectorHome.run {
                chipDateMonthSelector.text = it.MMMM_yyyy()
            }
        }

        walletsFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        wallets.observe(viewLifecycleOwner) {}

        incomeFlow.observe(viewLifecycleOwner) {
            binding.incStatusHome.txtIncomeStatus.text = it.toString()
        }

        expenseFlow.observe(viewLifecycleOwner) {
            binding.incStatusHome.txtExpenseStatus.text = it.toString()
        }

        balanceFlow.observe(viewLifecycleOwner) {
            binding.incStatusHome.txtBalanceStatus.text = it.toString()
        }
    }

    companion object {
        const val PLUS = "PLUS"
        const val MINUS = "MINUS"
        private const val MONTH_PICKER_TAG = "MONTH_PICKER"
    }
}