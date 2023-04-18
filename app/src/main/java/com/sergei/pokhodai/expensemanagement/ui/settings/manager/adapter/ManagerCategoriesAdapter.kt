package com.sergei.pokhodai.expensemanagement.ui.settings.manager.adapter

import androidx.core.view.isVisible
import com.sergei.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.sergei.pokhodai.expensemanagement.data.room.entities.ExpenseEntity
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.ItemEmptyBinding
import com.sergei.pokhodai.expensemanagement.databinding.ItemManagerCategoryBinding
import com.sergei.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.sergei.pokhodai.expensemanagement.utils.enums.Creater
import com.sergei.pokhodai.expensemanagement.utils.showMenu
import javax.inject.Inject

class ManagerCategoriesAdapter @Inject constructor(): BaseListAdapter<ManagerCategoriesAdapter.ItemManagerCategories>() {

    private var onClickEditOrDeleteActionListener: ((ActionManagerCategories) -> Unit)? = null

    fun setOnClickEditOrDeleteActionListener(action: (ActionManagerCategories) -> Unit) {
        onClickEditOrDeleteActionListener = action
    }

    override fun build() {
        baseViewHolder(ItemManagerCategories.WrapManagerCategory::class, ItemManagerCategoryBinding::inflate) { item ->
            binding.run {
                txtNameManagerCategory.text = item.expenseEntity.name
                ivManagerCategory.setImageResource(item.expenseEntity.icon.resId)
                btnDragAndDropManagerCategory.setOnThrottleClickListener {
                    root.context.showMenu(it) { creater ->
                        onClickEditOrDeleteActionListener?.invoke(
                            if (creater == Creater.EDIT) {
                                ActionManagerCategories.ActionEditManagerCategories(item.expenseEntity)
                            } else {
                                ActionManagerCategories.ActionDeleteManagerCategories(
                                    item.expenseEntity.id ?: 0
                                )
                            }
                        )
                    }
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

    sealed class ActionManagerCategories {
        data class ActionEditManagerCategories(val expenseEntity: ExpenseEntity): ActionManagerCategories()
        data class ActionDeleteManagerCategories(val id: Int) : ActionManagerCategories()
    }
}