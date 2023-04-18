package com.sergei.pokhodai.expensemanagement.ui.splash

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.sergei.pokhodai.expensemanagement.MainViewModel
import com.sergei.pokhodai.expensemanagement.databinding.FragmentSplashBinding
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