package com.sergei.pokhodai.expensemanagement.feature.category.impl

import com.sergei.pokhodai.expensemanagement.core.recycler.register.RecyclerRegister
import com.sergei.pokhodai.expensemanagement.core.router.provider.BottomNavigationVisibleProvider
import com.sergei.pokhodai.expensemanagement.core.router.provider.RouteProvider
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.DeleteAllCategoryUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.GetCategoryDefaultListUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.domain.usecase.SetCategoriesUseCase
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryBudgetTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryNameMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.mapper.CategoryTypeMapper
import com.sergei.pokhodai.expensemanagement.feature.category.api.router.CategoryRouter
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryDefaultRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.CategoryRepository
import com.sergei.pokhodai.expensemanagement.feature.category.impl.data.mapper.CategoryEntityMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.domain.DeleteAllCategoryUseCaseImpl
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.CategoryDialogViewModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.category.mapper.CategoryDialogMapper
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
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.editor.mapper.CategoryKindMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.CategoryManagerViewModel
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.mapper.CategoryManagerMapper
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager.CategoryManagerItem
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.manager.ui.manager.CategoryManagerItemView
import com.sergei.pokhodai.expensemanagement.feature.category.impl.presentation.mapper.CategoryBudgetTypeMapperImpl
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

    internal object Keys {
        const val CATEGORY_ICON = "CATEGORY_ICON"
        const val NEW_CATEGORY_CREATED_OR_EDIT = "NEW_CATEGORY_CREATED_OR_EDIT"
    }

    init {
        RecyclerRegister.Builder()
            .add(clazz = CategoryEditorItem.State::class.java, onView = ::CategoryEditorItemView)
            .add(clazz = ColorPickerItem.State::class.java, onView = ::ColorPickerItemView)
            .add(clazz = CategoryManagerItem.State::class.java, onView = ::CategoryManagerItemView)
            .build()
    }

    fun get(): Module {
        return module {
            singleOf(::CategoryRouterImpl) bind CategoryRouter::class
            singleOf(::CategoryRouteProviderImpl) bind RouteProvider::class
            singleOf(::CategoryBottomNavigationVisibleProviderImpl) bind BottomNavigationVisibleProvider::class

            singleOf(::CategoryNameMapperImpl) bind CategoryNameMapper::class
            singleOf(::CategoryTypeMapperImpl) bind CategoryTypeMapper::class
            singleOf(::CategoryBudgetTypeMapperImpl) bind CategoryBudgetTypeMapper::class

            singleOf(::GetCategoryDefaultListUseCaseImpl) bind GetCategoryDefaultListUseCase::class
            singleOf(::SetCategoriesUseCaseImpl) bind SetCategoriesUseCase::class
            singleOf(::DeleteAllCategoryUseCaseImpl) bind DeleteAllCategoryUseCase::class

            singleOf(::CategoryEntityMapper)
            singleOf(::CategoryDialogMapper)
            singleOf(::CategoryIconMapper)
            singleOf(::CategoryDefaultRepository)
            singleOf(::CategoryRepository)
            singleOf(::CategoryEditorMapper)
            singleOf(::CategoryKindMapper)
            singleOf(::CategoryManagerMapper)

            viewModelOf(::CategoryDialogViewModel)
            viewModelOf(::CategoryEditorViewModel)
            viewModelOf(::CategoryIconViewModel)
            viewModelOf(::CategoryManagerViewModel)
        }
    }
}