package com.sergei.pokhodai.expensemanagement.feature.category.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryDefaultRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.mapper.CategoryEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.CategoryDialogViewModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryDialogMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.GetCategoryDefaultListUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.SetCategoriesUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.CategoryEditorViewModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.mapper.CategoryEditorMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.category_editor.CategoryEditorItem
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.category_editor.CategoryEditorItemView
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.color_picker.ColorPickerItem
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.ui.color_picker.ColorPickerItemView
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.CategoryIconViewModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.icon_dialog.mapper.CategoryIconMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryKindMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryNameMapperImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryTypeMapperImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.CategoryBottomNavigationVisibleProviderImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.CategoryRouterImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.router.CategoryRouteProviderImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

object CategoryModule {

    init {
        RecyclerRegister.Builder()
            .add(clazz = CategoryEditorItem.State::class.java, onView = ::CategoryEditorItemView)
            .add(clazz = ColorPickerItem.State::class.java, onView = ::ColorPickerItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::CategoryRouterImpl) bind CategoryRouter::class
            singleOf(::CategoryRouteProviderImpl) bind RouteProvider::class
            singleOf(::CategoryBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class

            singleOf(::CategoryNameMapperImpl) bind CategoryNameMapper::class
            singleOf(::CategoryTypeMapperImpl) bind CategoryTypeMapper::class

            singleOf(::GetCategoryDefaultListUseCaseImpl) bind GetCategoryDefaultListUseCase::class
            singleOf(::SetCategoriesUseCaseImpl) bind SetCategoriesUseCase::class

            singleOf(::CategoryEntityMapper)
            singleOf(::CategoryDialogMapper)
            singleOf(::CategoryIconMapper)
            singleOf(::CategoryDefaultRepository)
            singleOf(::CategoryRepository)
            singleOf(::CategoryEditorMapper)
            singleOf(::CategoryKindMapper)

            viewModelOf(::CategoryDialogViewModel)
            viewModelOf(::CategoryEditorViewModel)
            viewModelOf(::CategoryIconViewModel)
        }
    }
}