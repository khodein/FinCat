package com.android.pokhodai.expensemanagement.ui.home.adapter

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.android.pokhodai.expensemanagement.base.ui.adapter.BasePagingAdapter
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.databinding.ItemWalletBinding
import com.android.pokhodai.expensemanagement.databinding.ItemWalletEmptyBinding
import com.android.pokhodai.expensemanagement.databinding.ItemWalletHeaderBinding
import com.android.pokhodai.expensemanagement.ui.home.add_wallet.adapter.CategoriesAdapter
import com.android.pokhodai.expensemanagement.utils.LocalDateFormatter
import com.android.pokhodai.expensemanagement.utils.dp
import com.android.pokhodai.expensemanagement.utils.getTextDate
import com.google.android.material.shape.CornerFamily

class WalletAdapter: BasePagingAdapter<WalletAdapter.ItemWallet>() {

    private val today = LocalDateFormatter.today()

    private var onLongClickActionListener: ((Int) -> Unit)? = null

    fun setOnLongClickActionListener(action: (Int) -> Unit) {
        onLongClickActionListener = action
    }

    override fun build() {
        baseViewHolder(ItemWallet.WrapWallet::class, ItemWalletBinding::inflate) { item ->
            binding.run {

                ivAmount.setImageResource(item.wallet.icons.resId)
                txtNameAmount.text = item.wallet.categoryName
                txtTypeAmount.text = item.wallet.type
                txtAmount.text = item.wallet.amount.toString()

                dividerBottomWallet.isVisible = !item.bottom
                val builderShapeModel = cardWallet.shapeAppearanceModel.toBuilder().apply {
                    val bottom = if (item.bottom) 8.dp else 0.dp
                    setBottomLeftCorner(CornerFamily.ROUNDED, bottom)
                    setBottomRightCorner(CornerFamily.ROUNDED, bottom)
                }
                cardWallet.shapeAppearanceModel = builderShapeModel.build()


                cardWallet.setOnLongClickListener {
                    item.wallet.id?.let {
                        onLongClickActionListener?.invoke(it)
                    }
                    true
                }
            }
        }

        baseViewHolder(ItemWallet.WrapHeader::class, ItemWalletHeaderBinding::inflate) { item ->
            binding.run {
                val builderShapeModel = cardWalletHeader.shapeAppearanceModel.toBuilder().apply {
                    setTopLeftCorner(CornerFamily.ROUNDED, 8.dp)
                    setTopRightCorner(CornerFamily.ROUNDED, 8.dp)
                }
                cardWalletHeader.shapeAppearanceModel = builderShapeModel.build()
                txtDateWallet.getTextDate(item.date, today)
                txtCountWallet.text = item.count
            }
        }

        baseViewHolder(ItemWallet.EmptyWallet::class, ItemWalletEmptyBinding::inflate) { _ ->
            binding.txtWalletEmpty.text = binding.root.context.getString(R.string.home_item_wallet_empty)
        }
    }

    sealed class ItemWallet {
        data class WrapWallet(val wallet: WalletEntity, var bottom: Boolean = false): ItemWallet()
        data class WrapHeader(val date: LocalDateFormatter, val count: String): ItemWallet()
        object EmptyWallet: ItemWallet()
    }
}