package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import com.sergei.pokhodai.expensemanagement.core.base.dialog.BaseBottomSheetDialogFragment
import com.sergei.pokhodai.expensemanagement.core.base.utils.applyPadding
import com.sergei.pokhodai.expensemanagement.core.base.utils.autoClean
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
    private val adapter by autoClean(init = ::RecyclerAdapter)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        itemsFlow.observe(
            this@UserLanguageDialog,
            adapter::submitList
        )

        topFlow
            .filterNotNull()
            .observe(
                this@UserLanguageDialog,
                binding.userLanguageTitle::setText
            )

        bottomFlow
            .filterNotNull()
            .observe(this@UserLanguageDialog) { state ->
                binding.userLanguageBottom.bindState(state)
                binding.userLanguageBottom.doOnLayout {
                    binding.userLanguageContainer.applyPadding(bottom = it.height)
                }
            }
    }

    private fun setAdapter() {
        binding.userLanguageItems.adapter = adapter
        binding.userLanguageItems.itemAnimator = null
    }
}