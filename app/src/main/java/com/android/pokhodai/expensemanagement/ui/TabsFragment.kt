package com.android.pokhodai.expensemanagement.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.utils.getMainController
import com.android.pokhodai.expensemanagement.databinding.FragmentTabsBinding
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : BaseFragment<FragmentTabsBinding>(FragmentTabsBinding::inflate) {

    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.fcvTabs) as NavHostFragment
    }

    override val navigationController by lazy {
        navHostFragment.navController
    }

    override fun initOnBackPressedDispatcher() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() = with(binding) {
        bnvTabs.setupWithNavController(navigationController)
        bnvTabs.itemIconTintList = null

        bnvTabs.setOnItemReselectedListener {
            val option = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                navigationController.graph.findStartDestination().id,
                inclusive = false,
                saveState = false
            ).build()

            runCatching {
                navigationController.navigate(it.itemId, null, option)
            }
        }
    }

    override fun setObservable() = with(navViewModel) {
        bnvVisible.observe(viewLifecycleOwner) {
            binding.bnvTabs.isVisible = it
        }

        navigateToDeepLink.observe(viewLifecycleOwner) { tabDeepLink ->
            binding.bnvTabs.selectedItemId = tabDeepLink.tabId

            val currentGraph = navigationController.graph.firstOrNull {
                it.id == tabDeepLink.tabId
            }

            (currentGraph as? NavGraph)?.let {
                val option = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                    it.findStartDestination().id,
                    inclusive = false,
                    saveState = false
                ).build()

                runCatching {
                    navigationController.navigate(tabDeepLink.uri, option)
                }
            }
        }
    }
}