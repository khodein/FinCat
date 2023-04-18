package com.sergei.pokhodai.expensemanagement.ui.search.adapters

import com.sergei.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.sergei.pokhodai.expensemanagement.data.models.CategoryTip
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.ItemTipBinding
import javax.inject.Inject

class CategoryTipsAdapter @Inject constructor(): BaseListAdapter<CategoryTip>() {

    private var onClickTipActionListener: ((String) -> Unit)? = null

    fun setOnClickTipActionListener(action: (String) -> Unit) {
        onClickTipActionListener = action
    }

    override fun build() {
        baseViewHolder(CategoryTip::class, ItemTipBinding::inflate) { item ->
            binding.run {
                ivIconTip.setImageResource(if (item.isFind) R.drawable.ic_check else item.icon.resId)
                root.setCardBackgroundColor(root.context.getColorStateList(if (item.isFind) R.color.blue_50 else R.color.white))
                root.strokeColor = root.context.getColor(if (item.isFind) R.color.blue_600 else R.color.grey_400)
                txtNameTip.text = item.name
                root.setOnClickListener {
                    onClickTipActionListener?.invoke(item.name)
                }
            }
        }
    }
}