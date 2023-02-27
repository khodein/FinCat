package com.android.pokhodai.expensemanagement.ui.home.add_wallet.expense.add_new_category.icons.adapter

import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.databinding.ItemIconBinding
import javax.inject.Inject

class IconsAdapter @Inject constructor() : BaseListAdapter<Int>() {

    private var onClickIconActionListener: ((Int) -> Unit)? = null

    fun setOnClickIconActionListener(action: (Int) -> Unit) {
        onClickIconActionListener = action
    }

    override fun build() {
        baseViewHolder(Int::class, ItemIconBinding::inflate) { item ->
            binding.run {
                root.setImageResource(item)
                root.setOnClickListener {
                    onClickIconActionListener?.invoke(item)
                }
            }
        }
    }
}