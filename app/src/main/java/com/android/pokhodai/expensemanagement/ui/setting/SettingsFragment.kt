package com.android.pokhodai.expensemanagement.ui.setting

import androidx.core.net.toUri
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }
}