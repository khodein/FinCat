package com.sergei.pokhodai.expensemanagement.feature.user.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserCurrencyUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserIdFlowUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.domain.GetUserSelfUseCase
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserAvatarArtMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.presentation.mapper.UserCurrencyNameMapper
import com.sergei.pokhodai.expensemanagement.feature.user.api.router.UserRouter
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserIdRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.UserRepository
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.mapper.UserEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.data.store.UserDataStoreKey
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserCurrencyUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserIdFlowUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserIdUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.domain.usecase.GetUserSelfUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.UserAvatarViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.UserCurrencyViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper.UserCurrencyMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.UserLanguageViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.language.mapper.UserLanguageMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.UserViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper.UserAvatarArtMapperImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.avatar.mapper.UserAvatarMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.currency.mapper.UserCurrencyNameMapperImpl
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list.UserListViewModel
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.list.mapper.UserListMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.self.mapper.UserMapper
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar.UserAvatarItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar.UserAvatarItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list.UserAvatarListItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.avatar_list.UserAvatarListItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.language.UserLanguageItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.tags_container.UserTagsContainerItemView
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user.UserListItem
import com.sergei.pokhodai.expensemanagement.feature.user.impl.presentation.ui.user.UserListItemView
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

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(UserDataStoreKey.DS_NAME)

    internal object Keys {
        const val USER_CURRENCY = "USER_CURRENCY"
        const val USER_AVATAR = "USER_AVATAR"
    }

    init {
        RecyclerRegister.Builder()
            .add(
                clazz = UserTagsContainerItem.State::class.java,
                onView = ::UserTagsContainerItemView
            )
            .add(clazz = UserLanguageItem.State::class.java, onView = ::UserLanguageItemView)
            .add(clazz = UserAvatarItem.State::class.java, onView = ::UserAvatarItemView)
            .add(clazz = UserAvatarListItem.State::class.java, onView = ::UserAvatarListItemView)
            .add(clazz = UserListItem.State::class.java, onView = ::UserListItemView)
            .build()
    }

    fun get(): Module {
        return module {
            single {
                UserIdRepository(dataStore = androidContext().dataStore)
            }

            singleOf(::UserRouterProviderImpl) bind RouteProvider::class
            singleOf(::UserRouterImpl) bind UserRouter::class
            singleOf(::UserBottomNavigationVisibilityProviderImpl) bind BottomNavigationVisibleProvider::class
            singleOf(::GetUserIdFlowUseCaseImpl) bind GetUserIdFlowUseCase::class

            singleOf(::GetUserIdUseCaseImpl) bind GetUserIdUseCase::class
            singleOf(::GetUserSelfUseCaseImpl) bind GetUserSelfUseCase::class
            singleOf(::GetUserCurrencyUseCaseImpl) bind GetUserCurrencyUseCase::class

            singleOf(::UserRepository)

            singleOf(::UserEntityMapper)

            singleOf(::UserLanguageMapper)
            singleOf(::UserMapper)
            singleOf(::UserCurrencyMapper)
            singleOf(::UserAvatarMapper)
            singleOf(::UserListMapper)
            singleOf(::UserAvatarArtMapperImpl) bind UserAvatarArtMapper::class
            singleOf(::UserCurrencyNameMapperImpl) bind UserCurrencyNameMapper::class

            viewModelOf(::UserLanguageViewModel)
            viewModelOf(::UserListViewModel)
            viewModelOf(::UserCurrencyViewModel)
            viewModelOf(::UserAvatarViewModel)
            viewModelOf(::UserViewModel)
        }
    }
}