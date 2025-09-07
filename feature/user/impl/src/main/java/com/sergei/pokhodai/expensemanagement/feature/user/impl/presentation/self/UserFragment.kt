package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
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
    private var adapter: RecyclerAdapter? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            onBackPressedCallback
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@UserFragment) { list ->
            adapter?.submitList(list)
        }

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
        this.adapter = RecyclerAdapter()
        binding.userItems.itemAnimator = null
        binding.userItems.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}