package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.R
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(init = FragmentSettingsBinding::bind)
    private val viewModel by viewModel<SettingsViewModel>()
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStart()
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(
            this@SettingsFragment,
            adapter::submitList
        )

        appBarFlow
            .filterNotNull()
            .observe(
                this@SettingsFragment,
                binding.settingsTb::bindState
            )
    }

    private fun setAdapter() {
        binding.settingsItems.adapter = adapter
    }
}