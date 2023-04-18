package com.sergei.pokhodai.expensemanagement.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.repositories.LanguageRepository
import com.sergei.pokhodai.expensemanagement.ui.date_picker.MonthPickerDialog
import com.sergei.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.sergei.pokhodai.expensemanagement.ui.home.creater.CreaterWalletFragment
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.utils.enums.Creater
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.FragmentHomeBinding
import com.sergei.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.sergei.pokhodai.expensemanagement.utils.dp
import com.sergei.pokhodai.expensemanagement.utils.getMonthLocalization
import com.sergei.pokhodai.expensemanagement.utils.navigateSafe
import com.sergei.pokhodai.expensemanagement.utils.observe
import com.sergei.pokhodai.expensemanagement.utils.observeLatest
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var adapter: WalletAdapter

    @Inject
    lateinit var languageRepository: LanguageRepository

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAvatar()
        mainViewModel.onSkipExit()
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

        adapter.setOnClickActionListener { view: View, descr: String ->
            val balloon = Balloon.Builder(requireContext())
                .setHeight(BalloonSizeSpec.WRAP)
                .setText(descr)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setDismissWhenTouchOutside(true)
                .setDismissWhenClicked(true)
                .setTextGravity(Gravity.START)
                .setCornerRadius(10f)
                .setPadding(10.dp.toInt())
                .setTextSize(10.dp)
                .setTextColor(requireContext().getColor(R.color.black))
                .setBackgroundColorResource(R.color.grey_100)
                .setBalloonAnimation(BalloonAnimation.OVERSHOOT)
                .setLifecycleOwner(viewLifecycleOwner)
                .build()
            balloon.showAlignBottom(view)
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
                chipDateMonthSelector.text = "${it.getMonthLocalization(requireContext())} ${it.yyyy(languageRepository.getLanguage())}"
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