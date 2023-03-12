package com.android.pokhodai.expensemanagement.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.data.models.User
import com.android.pokhodai.expensemanagement.databinding.FragmentSettingsBinding
import com.android.pokhodai.expensemanagement.ui.home.adapter.WalletAdapter
import com.android.pokhodai.expensemanagement.ui.settings.adapter.SettingAdapter
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var adapter: SettingAdapter

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.report_nav_graph)
    }

    override fun setAdapter() = with(binding) {
        rvSettings.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = User("Sergei", "Pokhodai", "john.doe@gmail.comsd;lsdl;;lds;lsd;lds;l;lds;lds;lsd;lds;lds;lsd")
        binding.run {
            txtNameSettings.text = user.fullName()
            txtEmailSettings.text = user.email
            avSetting.user = user
        }
    }

    override fun setObservable() = with(viewModel) {
        settingsFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun setListeners() = with(binding) {
        adapter.setOnClickActionListener {

        }
    }
}