package com.android.pokhodai.expensemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.android.pokhodai.expensemanagement.databinding.ActivityMainBinding
import com.android.pokhodai.expensemanagement.ui.pin_code.PinCodeFragmentDirections
import com.android.pokhodai.expensemanagement.utils.navigateSafe
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.rootContainer) as NavHostFragment).navController
    }

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservable()
    }

    private fun setObservable() = with(viewModel) {
        navigateFlow.observe(this@MainActivity) { result ->
            when(result) {
                is MainResult.UserEmptyResult -> {
                    navController.navigateSafe(RootNavGraphDirections.actionGlobalUserNavGraph())
                }
                is MainResult.EnterTheApplicationResult -> {
                    navController.navigateSafe(RootNavGraphDirections.actionGlobalTabsFragment())
                }
                is MainResult.PassCodeResult -> {
                    navController.navigateSafe(RootNavGraphDirections.actionGlobalPinCodeFragment())
                }
            }
        }
    }
}