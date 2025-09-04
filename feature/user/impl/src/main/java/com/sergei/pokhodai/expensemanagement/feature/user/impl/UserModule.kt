package com.sergei.pokhodai.expensemanagement.feature.user.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsFirstEntryAppUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.IsUserDataStoreEmptyUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserDataStoreRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserDataStoreMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store.DataStoreKey
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserIdUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserSelfUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.IsFirstEntryAppUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.IsUserDataStoreEmptyUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.UserCurrencyViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper.UserCurrencyMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.UserLanguageViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.mapper.UserLanguageMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.UserViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.mapper.UserMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.UserBottomNavigationVisibilityProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.UserRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.router.UserRouterProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object UserModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreKey.DS_NAME)

    internal object Keys {
        const val USER_CURRENCY = "USER_CURRENCY"
    }

    init {
        RecyclerRegister.Builder()
            .add(clazz = UserTagsContainerItem.State::class.java, onView = ::UserTagsContainerItemView)
            .add(clazz = UserLanguageItem.State::class.java, onView = ::UserLanguageItemView)
            .build()
    }

    fun get(): Module {
        return module {
            single {
                UserDataStoreRepository(
                    userDataStoreMapper = get<UserDataStoreMapper>(),
                    dataStore = androidContext().dataStore,
                )
            }

            singleOf(::UserRouterProviderImpl) bind RouteProvider::class
            singleOf(::UserRouterImpl) bind UserRouter::class
            singleOf(::UserBottomNavigationVisibilityProviderImpl) bind BottomNavigationVisibleProvider::class
            singleOf(::GetUserSelfUseCaseImpl) bind GetUserSelfUseCase::class

            singleOf(::IsUserDataStoreEmptyUseCaseImpl) bind IsUserDataStoreEmptyUseCase::class
            singleOf(::IsFirstEntryAppUseCaseImpl) bind IsFirstEntryAppUseCase::class
            singleOf(::GetUserIdUseCaseImpl) bind GetUserIdUseCase::class

            singleOf(::UserRepository)

            singleOf(::UserEntityMapper)
            singleOf(::UserDataStoreMapper)

            singleOf(::UserLanguageMapper)
            singleOf(::UserMapper)
            singleOf(::UserCurrencyMapper)

            viewModelOf(::UserLanguageViewModel)
            viewModelOf(::UserCurrencyViewModel)
            viewModelOf(::UserViewModel)
        }
    }
}