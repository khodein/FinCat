package com.android.pokhodai.expensemanagement.ui.home.creater.expense.add_new_category.icons

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.fragments.BaseBottomSheetDialogFragment
import com.android.pokhodai.expensemanagement.databinding.DialogCategoriesBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.expense.add_new_category.icons.adapter.IconsAdapter
import com.android.pokhodai.expensemanagement.utils.decorations.GridSpacingItemDecoration
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IconsDialog :
    BaseBottomSheetDialogFragment<DialogCategoriesBinding>(DialogCategoriesBinding::inflate) {

    @Inject
    lateinit var adapter: IconsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtTitleCategories.text = getString(R.string.icons_title)
    }

    override fun setAdapter() {
        binding.run {
            (rvCategories.layoutManager as GridLayoutManager).spanCount = 4
            rvCategories.addItemDecoration(GridSpacingItemDecoration(4, 50, false))
            rvCategories.adapter = adapter
            adapter.submitList(Icons.values().toList().drop(1))
        }
    }

    override fun setListeners() {
        adapter.setOnClickIconActionListener {
            setFragmentResult(CHOOSE_ICON, bundleOf(ICON to it))
            dismiss()
        }
    }

    companion object {
        const val CHOOSE_ICON = "CHOOSE_ICON"
        const val ICON = "ICON"
    }
}