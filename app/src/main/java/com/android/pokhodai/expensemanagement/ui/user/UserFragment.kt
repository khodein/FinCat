package com.android.pokhodai.expensemanagement.ui.user

import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment: BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val viewModel by viewModels<UserViewModel>()
}