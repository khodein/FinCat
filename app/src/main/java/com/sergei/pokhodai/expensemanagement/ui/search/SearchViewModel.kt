package com.sergei.pokhodai.expensemanagement.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.pokhodai.expensemanagement.data.models.CategoryTip
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.repositories.WalletFtsRepository
import com.sergei.pokhodai.expensemanagement.repositories.WalletRepository
import com.sergei.pokhodai.expensemanagement.ui.search.adapters.WalletSearchAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    private val walletFtsRepository: WalletFtsRepository,
) : ViewModel() {

    private val _categoryTipsFlow = MutableStateFlow<List<CategoryTip>>(emptyList())
    val categoryTipsFlow = _categoryTipsFlow.asStateFlow()

    private val _searchFlow = MutableStateFlow("")
    val searchFlow = _searchFlow.asStateFlow()

    private val _walletsSearchFlow = MutableStateFlow<List<WalletSearchAdapter.ItemWalletSearch.WrapWalletSearch>>(emptyList())
    val walletsSearchFlow = _walletsSearchFlow.map {
        it.ifEmpty { listOf(WalletSearchAdapter.ItemWalletSearch.ItemEmptySearch) }
    }

    init {
        findCategoryNamesByGroup()
    }

    private fun findCategoryNamesByGroup(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {
            _categoryTipsFlow.emit(walletRepository.findCategoryNamesByGroup().map {
                CategoryTip(
                    name = it.name,
                    icon = it.icon
                )
            })
        }
    }

    fun onChangeSearch(
        search: String,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {

            _searchFlow.value = search

            if (search.trim().isNotEmpty()) {
                _categoryTipsFlow.update { list ->
                    list.map {
                        it.copy(
                            isFind = false
                        )
                    }
                }

                _walletsSearchFlow.value = walletFtsRepository.searchByWallet(search).setMapWrapWalletsSearchList()
            }
        }
    }

    fun onClickTip(
        name: String,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineDispatcher) {

            _searchFlow.value = ""

            _categoryTipsFlow.update { list ->
                list.map {
                    it.copy(
                        isFind = if (name == it.name) {
                            !it.isFind
                        } else {
                            it.isFind
                        }
                    )
                }
            }

            categoryTipsFlow.value.filter {
                it.isFind
            }.map {
                it.name
            }.toTypedArray().let { filters ->
                _walletsSearchFlow.value = walletRepository.findCategoryNames(filters).setMapWrapWalletsSearchList()
            }
        }
    }

    private fun List<WalletEntity>.setMapWrapWalletsSearchList(): List<WalletSearchAdapter.ItemWalletSearch.WrapWalletSearch> {
        return this.map {
            WalletSearchAdapter.ItemWalletSearch.WrapWalletSearch(it)
        }
    }
}