package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.DialogUserAvatarBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserAvatarDialog : BaseBottomSheetDialogFragment(R.layout.dialog_user_avatar) {

    private val binding by viewBinding(init = DialogUserAvatarBinding::bind)
    private val viewModel by viewModel<UserAvatarViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@UserAvatarDialog) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(this@UserAvatarDialog) { text ->
                binding.userAvatarTitle.text = text
                binding.userAvatarTitle.doOnLayout {
                    binding.userAvatarItems.applyPadding(top = it.height)
                }
            }

        bottomFlow
            .filterNotNull()
            .observe(this@UserAvatarDialog) { state ->
                binding.userAvatarBottom.bindState(state)
                binding.userAvatarBottom.doOnLayout {
                    binding.userAvatarItems.applyPadding(bottom = it.height)
                }
            }
    }

    private fun setAdapter() {
        adapter = RecyclerAdapter()
        binding.userAvatarItems.adapter = adapter
        binding.userAvatarItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}