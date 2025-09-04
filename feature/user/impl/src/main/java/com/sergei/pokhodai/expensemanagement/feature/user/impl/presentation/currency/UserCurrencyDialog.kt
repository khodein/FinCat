package com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency

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
import com.sergei.pokhodai.expensemanagement.feature.user.impl.databinding.DialogUserCurrencyBinding
import kotlinx.coroutines.flow.filterNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class UserCurrencyDialog : BaseBottomSheetDialogFragment(R.layout.dialog_user_currency) {

    private val binding by viewBinding(init = DialogUserCurrencyBinding::bind)
    private val viewModel by viewModel<UserCurrencyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        userTagFlow
            .filterNotNull()
            .observe(
                this@UserCurrencyDialog,
                binding.userCurrencyTags::bindState
            )

        titleFlow
            .filterNotNull()
            .observe(
                this@UserCurrencyDialog,
                binding.userCurrencyTitle::setText
            )

        bottomFlow
            .filterNotNull()
            .observe(this@UserCurrencyDialog) { state ->
                binding.userCurrencyBottom.bindState(state)
                binding.userCurrencyBottom.doOnLayout {
                    binding.userCurrencyContainer.applyPadding(bottom = it.height)
                }
            }
    }
}