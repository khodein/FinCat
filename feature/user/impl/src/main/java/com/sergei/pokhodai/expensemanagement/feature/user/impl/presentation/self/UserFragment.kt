package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.FragmentUserBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding by viewBinding(init = FragmentUserBinding::bind)
    private val viewModel by viewModel<UserViewModel>()
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(
            this@UserFragment,
            adapter::submitList
        )

        topFlow
            .filterNotNull()
            .observe(
                this@UserFragment,
                binding.userTb::bindState
            )

        bottomFlow.observe(this@UserFragment) { state ->
            binding.userBottom.bindStateOptional(
                state = state,
                binder = ButtonItemView::bindState
            )
            binding.userBottom.doOnLayout {
                binding.userItems.updatePadding(bottom = it.height)
            }
        }
    }

    private fun setAdapter() {
        binding.userItems.itemAnimator = null
        binding.userItems.adapter = adapter
    }
}