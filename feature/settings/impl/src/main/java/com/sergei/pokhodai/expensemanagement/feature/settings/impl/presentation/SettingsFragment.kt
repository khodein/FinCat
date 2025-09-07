package com.sergei.pokhodai.expensemanagement.feature.settings.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
        viewModel.onStart()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@SettingsFragment) { list ->
            adapter?.submitList(list)
        }

        requestFlow.observe(
            this@SettingsFragment,
            binding.settingsRequest::bindState
        )

        topFlow
            .filterNotNull()
            .observe(
                this@SettingsFragment,
                binding.settingsTb::bindState
            )
    }

    private fun setAdapter() {
        this.adapter = RecyclerAdapter()
        binding.settingsItems.adapter = adapter
        binding.settingsItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}