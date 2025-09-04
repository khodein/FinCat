package com.sergei.pokhodai.expensemanagement.core.router

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination

interface Router {
    fun setProvider(provider: Provider)
    fun create(): NavGraphModel
    fun onDestinationChangedListener(id: Int): Boolean
    fun navigate(
        navAnimation: NavAnimation? = null,
        contract: RouterContract,
    )
    fun pop()
    interface Provider {
        val navController: NavController
    }

    class NavGraphModel(
        val navGraph: NavGraph?,
        val tabs: List<TabDestination>,
    )
}