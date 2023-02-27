package com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter

import android.os.Parcelable
import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.databinding.ItemCategoryBinding
import com.android.pokhodai.expensemanagement.databinding.ItemEmptyBinding
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.income.IncomeViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(): BaseListAdapter<CategoriesAdapter.Categories>() {

    private var onClickCategoryActionListener: ((Categories) -> Unit)? = null

    fun setOnClickCategoryActionListener(action: (Categories) -> Unit) {
        onClickCategoryActionListener = action
    }

    override fun build() {
        baseViewHolder(Categories::class, ItemCategoryBinding::inflate) { item ->
            binding.run {
                txtIncome.text = item.name
                ivIncome.setImageResource(item.resId)
                root.setOnClickListener {
                    onClickCategoryActionListener?.invoke(item)
                }
            }
        }
    }

    @Parcelize
    data class Categories(
        val name: String,
        val resId: Int
    ): Parcelable
}