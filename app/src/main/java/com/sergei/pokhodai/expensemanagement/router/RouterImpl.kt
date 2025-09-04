package com.sergei.pokhodai.expensemanagement.router

import android.os.Handler
import android.os.Looper
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.createGraph
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import java.lang.ref.WeakReference
import androidx.navigation.get
import androidx.navigation.navigation
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.animation.NavAnimation
import com.sergei.pokhodai.expensemanagement.core.router.contract.RouterContract
import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider

internal class RouterImpl(
    bottomNavigationVisibleProviderList: List<BottomNavigationVisibleProvider>,
    private val routeProviderList: List<RouteProvider>,
    private val tabProviderLis: List<TabProvider>
) : Router {
    private var _provider: WeakReference<Router.Provider>? = null
    private var navGraphModel: WeakReference<Router.NavGraphModel>? = null
    private val handler = Handler(Looper.getMainLooper())
    private val provider: Router.Provider?
        get() = _provider?.get()

    override fun setProvider(provider: Router.Provider) {
        this._provider = WeakReference(provider)
    }

    private val bottomNavigationIsNotVisibleIds: List<Int> = bottomNavigationVisibleProviderList.flatMap { it.getRouteListIsNotVisible() }

    override fun pop() {
        val navController = provider?.navController ?: return
        navController.popBackStack()
    }

    override fun create(): Router.NavGraphModel {
        val cacheNavGraph = navGraphModel?.get()
        if (cacheNavGraph != null) {
            return cacheNavGraph
        }

        val destinations = routeProviderList.flatMap { it.getDestination() }
        val tabs = tabProviderLis.flatMap { it.getDestination() }.sortedBy { it.order }
        val startDestinationTab = tabs.first { it.isStart }

        val navGraph = provider?.navController?.createGraph(
            startDestination = startDestinationTab.clazz
        ) {
            tabs.forEach { destination ->
                navigation(
                    route = destination.clazz,
                    startDestination = destination.tab.startDestination,
                    builder = { buildRoute(destinations) }
                )
            }
        }

        return Router.NavGraphModel(
            navGraph = navGraph,
            tabs = tabs
        ).also {
            this.navGraphModel = WeakReference(it)
        }
    }

    override fun onDestinationChangedListener(id: Int): Boolean {
        val isNotVisibleId = bottomNavigationIsNotVisibleIds.any { it == id }
        return !isNotVisibleId
    }

    private fun NavGraphBuilder.buildRoute(destinations: List<RouteDestination>) {
        destinations.forEach { destination ->
            when (val type = destination.type) {
                is RouteDestination.Type.DialogType -> {
                    addDestination(
                        DialogFragmentNavigatorDestinationBuilder(
                            provider[DialogFragmentNavigator::class],
                            type.contract,
                            emptyMap(),
                            type.clazz
                        ).build()
                    )
                }

                is RouteDestination.Type.FragmentType -> {
                    addDestination(
                        FragmentNavigatorDestinationBuilder(
                            provider[FragmentNavigator::class],
                            type.contract,
                            emptyMap(),
                            type.clazz,
                        ).build()
                    )
                }
            }
        }
    }

    override fun navigate(
        navAnimation: NavAnimation?,
        contract: RouterContract
    ) {
        val controller = provider?.navController ?: return
        val navRunnable = Runnable {
            val navOptions = NavOptions
                .Builder()
                .navAnimation(navAnimation)
                .build()

            runCatching {
                controller.navigate(contract, navOptions)
            }
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            navRunnable.run()
        } else {
            handler.post(navRunnable)
        }
    }

    private fun NavOptions.Builder.navAnimation(navAnimation: NavAnimation?): NavOptions.Builder {
        return when (navAnimation) {
            NavAnimation.FADE -> this.fade()
            NavAnimation.SLIDE -> this.slide()
            NavAnimation.FADE_SLIDE -> this.fadeSlide()
            NavAnimation.ZOOM -> this.zoom()
            else -> this
        }
    }

    private fun NavOptions.Builder.fade(): NavOptions.Builder {
        return this
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
    }

    private fun NavOptions.Builder.slide(): NavOptions.Builder {
        return this
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
    }

    private fun NavOptions.Builder.zoom(): NavOptions.Builder {
        return this
            .setEnterAnim(R.anim.zoom_in)
            .setExitAnim(R.anim.zoom_out)
            .setPopEnterAnim(R.anim.zoom_in)
            .setPopExitAnim(R.anim.zoom_out)
    }

    private fun NavOptions.Builder.fadeSlide(): NavOptions.Builder {
        return this
            .setEnterAnim(R.anim.slide_fade_in)
            .setExitAnim(R.anim.slide_fade_out)
            .setPopEnterAnim(R.anim.slide_fade_in)
            .setPopExitAnim(R.anim.slide_fade_out)
    }
}