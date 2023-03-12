package com.android.pokhodai.expensemanagement.ui.settings.manager.adapter

import androidx.core.view.isVisible
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.android.pokhodai.expensemanagement.databinding.ItemEmptyBinding
import com.android.pokhodai.expensemanagement.databinding.ItemManagerCategoryBinding
import com.android.pokhodai.expensemanagement.ui.home.creater.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import java.util.*
import javax.inject.Inject

class ManagerCategoriesAdapter @Inject constructor(): BaseListAdapter<ManagerCategoriesAdapter.ItemManagerCategories>() {

    private var onClickEditActionListener: ((ExpenseEntity) -> Unit)? = null

    fun setOnClickEditActionListener(action: (ExpenseEntity) -> Unit) {
        onClickEditActionListener = action
    }

    override fun build() {
        baseViewHolder(ItemManagerCategories.WrapManagerCategory::class, ItemManagerCategoryBinding::inflate) { item ->
            binding.run {
                txtNameManagerCategory.text = item.expenseEntity.name
                ivManagerCategory.setImageResource(item.expenseEntity.icon.resId)
                btnEditManagerCategory.setOnThrottleClickListener {
                    onClickEditActionListener?.invoke(item.expenseEntity)
                }
            }
        }

        baseViewHolder(ItemManagerCategories.ItemEmpty::class, ItemEmptyBinding::inflate) {
            binding.run {
                root.isVisible = true
                root.text = root.context.getString(R.string.manager_categories_empty)
            }
        }
    }

    sealed class ItemManagerCategories {
        data class WrapManagerCategory(val expenseEntity: ExpenseEntity): ItemManagerCategories()
        object ItemEmpty: ItemManagerCategories()
    }
}