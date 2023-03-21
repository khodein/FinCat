package com.android.pokhodai.expensemanagement.ui.splash

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import com.android.pokhodai.expensemanagement.MainViewModel
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onResume() {
        super.onResume()
        mainViewModel.onPrepareProject()
    }
}