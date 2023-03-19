package com.android.pokhodai.expensemanagement.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseFragment
import com.android.pokhodai.expensemanagement.databinding.FragmentSearchBinding
import com.android.pokhodai.expensemanagement.ui.search.adapters.CategoryTipsAdapter
import com.android.pokhodai.expensemanagement.ui.search.adapters.WalletSearchAdapter
import com.android.pokhodai.expensemanagement.utils.observe
import com.android.pokhodai.expensemanagement.utils.setUniqueText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override val isBnvVisible: Boolean = false

    private val viewModel by viewModels<SearchViewModel>()

    @Inject
    lateinit var adapterWalletSearch: WalletSearchAdapter

    @Inject
    lateinit var adapter: CategoryTipsAdapter

    override fun setAdapter() {
        binding.run {
            rvCategoriesSearch.adapter = adapter
            rvWalletsSearch.adapter = adapterWalletSearch
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSearch.requestFocus()
        windowInsetsController.show(WindowInsetsCompat.Type.ime())
    }

    override fun setObservable() = with(viewModel) {
        categoryTipsFlow.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.rvCategoriesSearch.isVisible = it.isNotEmpty()
        }

        searchFlow.observe(viewLifecycleOwner) {
            binding.txtSearch.setUniqueText(it)
        }

        walletsSearchFlow.observe(viewLifecycleOwner) {
            adapterWalletSearch.submitList(it)
        }
    }

    override fun setListeners() = with(binding) {
        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (txtSearch.text?.trim()?.isNotEmpty() == true) {
                    viewModel.onChangeSearch(txtSearch.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        txtSearch.doAfterTextChanged {
            viewModel.onChangeSearch(it.toString())
        }

        tilSearch.setStartIconOnClickListener {
            onBackPressed()
        }

        adapter.setOnClickTipActionListener {
            viewModel.onClickTip(it)
        }
    }
}