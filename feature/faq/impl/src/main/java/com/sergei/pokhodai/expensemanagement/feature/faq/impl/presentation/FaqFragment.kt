package com.sergei.pokhodai.expensemanagement.feature.faq.impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.R
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.databinding.FragmentFaqBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FaqFragment : Fragment(R.layout.fragment_faq) {

    private val binding by viewBinding(init = FragmentFaqBinding::bind)
    private val viewModel by viewModel<FaqViewModel>()

    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@FaqFragment) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(this@FaqFragment, binding.faqTop::bindState)
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.faqItems.adapter = adapter
        binding.faqItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}