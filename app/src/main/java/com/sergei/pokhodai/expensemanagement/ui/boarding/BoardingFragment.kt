package com.sergei.pokhodai.expensemanagement.ui.boarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.databinding.FragmentBoardingBinding
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