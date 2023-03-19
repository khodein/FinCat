package com.android.pokhodai.expensemanagement.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentHomeBinding
import com.android.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.android.pokhodai.expensemanagement.ui.home.creater.CreaterWalletFragment
import com.android.pokhodai.expensemanagement.ui.date_picker.MonthPickerDialog
import com.android.pokhodai.expensemanagement.utils.*
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var adapter: WalletAdapter

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAvatar()
    }

    private fun setAvatar() {
        binding.avHome.user = viewModel.userDataSource.user
    }

    override fun setAdapter() = with(binding) {
        rvHome.itemAnimator = null
        rvHome.adapter = adapter
    }

    override fun setListeners() = with(binding) {
        btnAddNewHome.setOnClickListener {
            navigationController.navigateSafe(
                HomeFragmentDirections.actionHomeFragmentToCreaterWalletFragment(
                    type = Creater.CREATE
                )
            )
        }

        srlHome.setOnRefreshListener {
            onSwipeRefresh()
        }

        btnSearchHome.setOnClickListener {
            navigationController.navigateSafe(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }

        incMonthSelectorHome.run {
            btnLeftMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(MINUS)
            }

            chipDateMonthSelector.setOnThrottleClickListener {
                navigationController.navigateSafe(
                    HomeFragmentDirections.actionHomeFragmentToMonthPickerDialog(
                        viewModel.dateFlow.value.timeInMillis()
                    )
                )
            }

            btnRightMonthSelector.setOnClickListener {
                viewModel.onChangePreviousOrNextMonth(PLUS)
            }
        }

        adapter.setOnLongClickActionListener { action ->
            when (action) {
                is WalletAdapter.ActionWallet.ActionDeleteWallet -> {
                    viewModel.onClickDeleteWallet(action.id)
                }
                is WalletAdapter.ActionWallet.ActionEditWallet -> {
                    navigationController.navigateSafe(
                        HomeFragmentDirections.actionHomeFragmentToCreaterWalletFragment(
                            type = Creater.EDIT,
                            wallet = action.wallet
                        )
                    )
                }
            }

        }

        setFragmentResultListener(CreaterWalletFragment.ADD_NEW_WALLET) { _, _ ->
            onSwipeRefresh()
        }

        setFragmentResultListener(MonthPickerDialog.CHANGE_DATE) { _, bundle ->
            viewModel.onChangeMonthDate(LocalDateFormatter.from(bundle.getLong(MonthPickerDialog.DATE)))
        }
    }

    private fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
        navViewModel.onSwipeRefresh()
    }

    @SuppressLint("SetTextI18n")
    override fun setObservable() = with(viewModel) {
        dateFlow.observe(viewLifecycleOwner) {
            binding.incMonthSelectorHome.run {
                chipDateMonthSelector.text = it.MMMM_yyyy()
            }
        }

        walletsFlow.observeLatest(viewLifecycleOwner) {
            adapter.submitData(it)
        }

        adapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .filter { it.refresh is LoadState.NotLoading }
            .observe(viewLifecycleOwner) {
                if (binding.srlHome.isRefreshing) {
                    binding.rvHome.scrollToPosition(0)
                    binding.srlHome.isRefreshing = false
                }
            }

        statusFlow.observe(viewLifecycleOwner) { triple ->
            binding.incStatusHome.run {
                val currencyText = getString(currency.resId)
                txtIncomeStatus.text = "${triple.first}$currencyText"
                txtBalanceStatus.text = "${triple.second}$currencyText"
                txtExpenseStatus.text = "${triple.third}$currencyText"
            }
        }
    }

    companion object {
        const val PLUS = "PLUS"
        const val MINUS = "MINUS"
    }
}