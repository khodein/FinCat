package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.bindStateOptional
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.FragmentUserListBinding
import com.sergei.pokhodai.expensemanagement.uikit.button.ButtonItemView
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private val binding by viewBinding(init = FragmentUserListBinding::bind)
    private val viewModel by viewModel<UserListViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
        viewModel.onStart()
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.userListItems.adapter = adapter
        binding.userListItems.itemAnimator = null
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@UserListFragment) { list ->
            adapter?.submitList(list)
        }

        requestFlow.observe(
            this@UserListFragment,
            binding.userListRequest::bindState
        )

        topFlow
            .filterNotNull()
            .observe(this@UserListFragment, binding.userListTb::bindState)

        bottomFlow
            .observe(this@UserListFragment) { state ->
                binding.userListButton.bindStateOptional(
                    state = state,
                    binder = ButtonItemView::bindState
                )

                if (state == null) {
                    binding.userListItems.applyPadding(bottom = 0)
                } else {
                    binding.userListButton.doOnLayout {
                        binding.userListItems.updatePadding(bottom = it.height)
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}