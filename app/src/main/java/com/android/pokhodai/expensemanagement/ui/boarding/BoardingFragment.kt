package com.android.pokhodai.expensemanagement.ui.boarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.MainViewModel
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentBoardingBinding
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardingFragment: BaseFragment<FragmentBoardingBinding>(FragmentBoardingBinding::inflate) {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onBackPressed() {
        viewModel.onClickExitApp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFirstEntry()
    }

    override fun setListeners() = with(binding) {
        btnStartBoarding.setOnThrottleClickListener {
            viewModel.onPrepareProject()
        }
    }
}