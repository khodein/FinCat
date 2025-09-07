package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.base.utils.viewBinding
import com.sergei.pokhodai.expensemanagement.core.recycler.adapter.RecyclerAdapter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.R
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.DialogUserLanguageBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserLanguageDialog : BaseBottomSheetDialogFragment(R.layout.dialog_user_language) {
    private val binding by viewBinding(init = DialogUserLanguageBinding::bind)
    private val viewModel by viewModel<UserLanguageViewModel>()
    private var adapter: RecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(this@UserLanguageDialog) { list ->
            adapter?.submitList(list)
        }

        topFlow
            .filterNotNull()
            .observe(this@UserLanguageDialog) { text ->
                binding.userLanguageTitle.text = text
                binding.userLanguageTitle.doOnLayout {
                    binding.userLanguageItems.applyPadding(top = it.height)
                }
            }

        bottomFlow
            .filterNotNull()
            .observe(this@UserLanguageDialog) { state ->
                binding.userLanguageBottom.bindState(state)
                binding.userLanguageBottom.doOnLayout {
                    binding.userLanguageItems.applyPadding(bottom = it.height)
                }
            }
    }

    private fun setAdapter() {
        this.adapter = RecyclerAdapter()
        binding.userLanguageItems.adapter = adapter
        binding.userLanguageItems.itemAnimator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}