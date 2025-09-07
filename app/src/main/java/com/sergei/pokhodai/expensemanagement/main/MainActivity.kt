package com.sergei.pokhodai.expensemanagement.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewGroupCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sergei.pokhodai.expensemanagement.core.base.utils.observe
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.base.utils.setLightNavigationAndStatusBars
import com.sergei.pokhodai.expensemanagement.core.router.support.SupportRouter
import com.sergei.pokhodai.expensemanagement.core.support.api.ResManager
import com.sergei.pokhodai.expensemanagement.databinding.ActivityMainBinding
import kotlinx.serialization.InternalSerializationApi
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.view.get
import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination

internal class MainActivity : AppCompatActivity(),
    Router.Provider,
    SupportRouter.Provider {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()
    private val router by inject<Router>()
    private val supportRouter by inject<SupportRouter>()
    private val resManager by inject<ResManager>()
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(binding.contentMain.id) as NavHostFragment
    }
    override val navController by lazy { navHostFragment.navController }

    private val onDestinationChangedListener by lazy {
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            binding.bnvMain.isVisible = router.onDestinationChangedListener(destination.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewGroupCompat.installCompatInsetsDispatch(binding.root)

        setRootInsets()

        window.setLightNavigationAndStatusBars(
            isAppearanceLightNavigationBars = false,
            isAppearanceLightStatusBars = true
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        router.setProvider(this)
        supportRouter.setProvider(this)

        setNavigationTabs()

        setObservable()
    }

    @SuppressLint("RestrictedApi")
    @OptIn(InternalSerializationApi::class)
    private fun setNavigationTabs() {
        val navGraphModel = router.create()
        val graph = navGraphModel.navGraph
        val tabs = navGraphModel.tabs

        if (graph != null) {
            navController.graph = graph
        }

        if (binding.bnvMain.menu.isEmpty()) {
            setBottomNavigationTabs(tabs)
        }

        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    private fun updateBottomNavigationMenu() {
        if (binding.bnvMain.menu.isNotEmpty()) {
            binding.bnvMain.menu.clear()
            setBottomNavigationTabs(router.create().tabs)
        }
    }

    private fun setBottomNavigationTabs(tabs: List<TabDestination>) {
        var order = 0
        tabs.forEach { destination ->
            order = order + 1
            binding.bnvMain.menu.add(
                Menu.NONE,
                destination.id,
                order,
                resManager.getString(destination.tab.nameResId)
            ).setIcon(destination.tab.iconResId)
        }
        binding.bnvMain.itemIconTintList = null
        binding.bnvMain.setupWithNavController(navController)
    }

    private fun setRootInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val bottom = if (bars.bottom > imeHeight) {
                bars.bottom
            } else {
                imeHeight
            }
            v.updatePadding(
                left = bars.left,
                top = 0,
                right = bars.right,
                bottom = bottom,
            )
            binding.contentMain.updatePadding(top = bars.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setObservable() = with(viewModel) {
        languageFlow.observe(this@MainActivity) {
            updateBottomNavigationMenu()
        }
    }

    override fun getSupportRouterNavHostFragment() = navHostFragment
    override fun getSupportActivityContext(): Context = this
}