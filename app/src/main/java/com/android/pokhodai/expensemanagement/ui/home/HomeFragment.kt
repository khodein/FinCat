package com.android.pokhodai.expensemanagement.ui.home

import androidx.core.net.toUri
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }
}