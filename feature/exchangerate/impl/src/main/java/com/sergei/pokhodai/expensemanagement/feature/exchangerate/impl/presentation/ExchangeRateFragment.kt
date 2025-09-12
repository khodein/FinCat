package com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.R
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.databinding.FragmentExchangeRateBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ExchangeRateFragment : Fragment(R.layout.fragment_exchange_rate) {

    private val binding by viewBinding(init = FragmentExchangeRateBinding::bind)
    private val viewModel by viewModel<ExchangeRateViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@ExchangeRateFragment) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(
                this@ExchangeRateFragment,
                binding.exchangeRateTb::bindState
            )

        requestFlow.observe(
            this@ExchangeRateFragment,
            binding.exchangeRateRequest::bindState
        )
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.exchangeRateItems.adapter = adapter
        binding.exchangeRateItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}