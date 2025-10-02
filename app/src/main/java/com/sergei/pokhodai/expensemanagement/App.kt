package com.sergei.pokhodai.expensemanagement

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.sergei.pokhodai.expensemanagement.config.ConfigProviderImpl
import com.sergei.pokhodai.expensemanagement.core.configprovider.ConfigProvider
import com.sergei.pokhodai.expensemanagement.core.eventbus.impl.EventBusModule
import com.sergei.pokhodai.expensemanagement.core.network.NetworkModule
import com.sergei.pokhodai.expensemanagement.core.router.Router
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.TabProvider
import com.sergei.pokhodai.expensemanagement.core.support.api.manager.LocaleManager
import com.sergei.pokhodai.expensemanagement.core.support.impl.SupportModule
import com.sergei.pokhodai.expensemanagement.database.impl.RoomModule
import com.sergei.pokhodai.expensemanagement.feature.calendar.impl.CalendarMonthModule
import com.sergei.pokhodai.expensemanagement.feature.category.impl.CategoryModule
import com.sergei.pokhodai.expensemanagement.feature.eventeditor.impl.EventEditorModule
import com.sergei.pokhodai.expensemanagement.feature.exchangerate.impl.ExchangeRateModule
import com.sergei.pokhodai.expensemanagement.feature.faq.impl.FaqModule
import com.sergei.pokhodai.expensemanagement.feature.pincode.impl.PinCodeModule
import com.sergei.pokhodai.expensemanagement.feature.report.impl.ReportModule
import com.sergei.pokhodai.expensemanagement.feature.settings.impl.SettingsModule
import com.sergei.pokhodai.expensemanagement.feature.user.impl.UserModule
import com.sergei.pokhodai.expensemanagement.home.impl.HomeModule
import com.sergei.pokhodai.expensemanagement.main.MainViewModel
import com.sergei.pokhodai.expensemanagement.router.RouterImpl
import com.sergei.pokhodai.expensemanagement.uikit.UikitModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal class App : Application(), SingletonImageLoader.Factory {

    private val localeManager by inject<LocaleManager>()

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger()
            androidContext(this@App)
            fragmentFactory()

            modules(
                AppModule.get(),
                FaqModule.get(),
                CalendarMonthModule.get(),
                ExchangeRateModule.get(),
                SupportModule.get(),
                EventBusModule.get(),
                NetworkModule.get(),
                RoomModule.get(),
                ReportModule.get(),
                CategoryModule.get(),
                EventEditorModule.get(),
                HomeModule.get(),
                UserModule.get(),
                SettingsModule.get(),
                PinCodeModule.get()
            )
        }

        localeManager.updateLanguage(localeManager.getLanguage())
    }

    override fun newImageLoader(context: Context): ImageLoader {
        return AppModule.newImageLoader(context)
    }

    private object AppModule {

        init {
            UikitModule.register()
        }

        fun get(): Module {
            return module {
                single {
                    val routeProviderList = getKoin().getAll<RouteProvider>()
                    val tabProviderList = getKoin().getAll<TabProvider>()
                    val bottomNavigationVisibleProviderList =
                        getKoin().getAll<BottomNavigationVisibleProvider>()
                    RouterImpl(
                        bottomNavigationVisibleProviderList = bottomNavigationVisibleProviderList,
                        routeProviderList = routeProviderList,
                        tabProviderLis = tabProviderList
                    )
                } bind Router::class

                viewModelOf(::MainViewModel)
                singleOf(::ConfigProviderImpl) bind ConfigProvider::class
            }
        }

        fun newImageLoader(
            context: PlatformContext,
        ): ImageLoader {
            return ImageLoader.Builder(context).memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(
                        context = context,
                        percent = 0.25
                    )
                    .build()
            }
                .crossfade(true)
                .build()
        }
    }
}