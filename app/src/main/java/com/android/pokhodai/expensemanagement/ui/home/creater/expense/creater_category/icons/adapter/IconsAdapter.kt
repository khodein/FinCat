package com.android.pokhodai.expensemanagement.ui.home.creater.expense.creater_category.icons.adapter

import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.databinding.ItemIconBinding
import com.android.pokhodai.expensemanagement.utils.enums.Icons
import javax.inject.Inject

class IconsAdapter @Inject constructor() : BaseListAdapter<Icons>() {

    private var onClickIconActionListener: ((Icons) -> Unit)? = null

    fun setOnClickIconActionListener(action: (Icons) -> Unit) {
        onClickIconActionListener = action
    }

    override fun build() {
        baseViewHolder(Icons::class, ItemIconBinding::inflate) { item ->
            binding.run {
                root.setImageResource(item.resId)
                root.setOnClickListener {
                    onClickIconActionListener?.invoke(item)
                }
            }
        }
    }
}