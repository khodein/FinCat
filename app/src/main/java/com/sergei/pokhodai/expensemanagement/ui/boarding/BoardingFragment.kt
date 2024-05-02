package com.sergei.pokhodai.expensemanagement.ui.boarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.FragmentBoardingBinding
import com.sergei.pokhodai.expensemanagement.utils.observe
import com.sergei.pokhodai.expensemanagement.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardingFragment: Fragment(R.layout.fragment_boarding) {

    private val binding by viewBinding { FragmentBoardingBinding.bind(it) }
    private val viewModel by viewModels<BoardingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        buttonItemStateFlow.observe(viewLifecycleOwner) { state ->
            state?.let(binding.boardingBtn::bindState)
        }

        boardingItemStateFlow.observe(viewLifecycleOwner) { state ->
            state?.let(binding.boardingItem::bindState)
        }
    }
}