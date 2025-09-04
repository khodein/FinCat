package com.sergei.pokhodai.expensemanagement.home.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.feature.home.impl.R
import com.sergei.pokhodai.expensemanagement.feature.home.impl.databinding.FragmentHomeBinding
import com.sergei.pokhodai.expensemanagement.home.impl.presentation.decoration.HomeDecoration
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(init = FragmentHomeBinding::bind)
    private val viewModel by viewModel<HomeViewModel>()
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStart()
        setAdapter()
        setObservable()
    }

    private fun setAdapter() {
        binding.homeList.itemAnimator = null
        binding.homeList.addItemDecoration(HomeDecoration())
        binding.homeList.adapter = adapter
    }

    private fun setObservable() = with(viewModel) {
        bottomFlow
            .filterNotNull()
            .observe(
                this@HomeFragment,
                binding.homeBottom::bindState
            )

        itemsFlow
            .observe(
                this@HomeFragment,
                adapter::submitList
            )

        requestFlow.observe(
            this@HomeFragment,
            binding.homeRequest::bindState
        )

        topFlow
            .filterNotNull()
            .observe(
                this@HomeFragment,
                binding.homeCalendar::bindState
            )
    }
}