package com.android.pokhodai.expensemanagement.utils.touch_helpers

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ItemTouchHelperCallback @Inject constructor() : ItemTouchHelper.Callback() {

    private var onMoveActionListener: ((Int, Int) -> Unit)? = null

    fun setOnMoveActionListener(action: (Int, Int) -> Unit) {
        onMoveActionListener = action
    }

    private var onItemDismissActionListener: ((Int) -> Unit)? = null

    fun setOnItemDismissActionListener(action: (Int) -> Unit) {
        onItemDismissActionListener = action
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        onMoveActionListener?.invoke(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDismissActionListener?.invoke(viewHolder.bindingAdapterPosition)
    }
}