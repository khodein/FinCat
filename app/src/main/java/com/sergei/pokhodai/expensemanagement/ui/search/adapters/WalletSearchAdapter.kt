package com.sergei.pokhodai.expensemanagement.ui.search.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import com.sergei.pokhodai.expensemanagement.base.ui.adapter.BaseListAdapter
import com.sergei.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.sergei.pokhodai.expensemanagement.R
import com.sergei.pokhodai.expensemanagement.databinding.ItemEmptyBinding
import com.sergei.pokhodai.expensemanagement.databinding.ItemWalletBinding
import com.sergei.pokhodai.expensemanagement.utils.dp
import javax.inject.Inject

class WalletSearchAdapter @Inject constructor() :
    BaseListAdapter<WalletSearchAdapter.ItemWalletSearch>() {

    @SuppressLint("SetTextI18n")
    override fun build() {
        baseViewHolder(ItemWalletSearch.WrapWalletSearch::class, ItemWalletBinding::inflate) { item ->
            binding.run {
                val builderShapeModel = cardWallet.shapeAppearanceModel.toBuilder().apply {
                    setAllCornerSizes(8.dp)
                }
                cardWallet.shapeAppearanceModel = builderShapeModel.build()
                dividerTopWallet.isVisible = false
                dividerBottomWallet.isVisible = false
                ivAmount.setImageResource(item.wallet.icons.resId)
                txtNameAmount.text = item.wallet.categoryName
                txtTypeAmount.text = item.wallet.type
                txtAmount.text = "${item.wallet.amount}${root.context.getString(item.wallet.currency.resId)}"
                cardWallet.cardElevation = 0f
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = 10.dp.toInt()
                }
            }
        }

        baseViewHolder(ItemWalletSearch.ItemEmptySearch::class, ItemEmptyBinding::inflate) {
            binding.root.text = binding.root.context.getString(R.string.search_empty)
        }
    }

    sealed class ItemWalletSearch {
        data class WrapWalletSearch(val wallet: WalletEntity) : ItemWalletSearch()
        object ItemEmptySearch : ItemWalletSearch()
    }
}