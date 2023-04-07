package com.android.pokhodai.expensemanagement.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.InsetDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.core.view.isVisible
import com.android.pokhodai.expensemanagement.R
import com.android.pokhodai.expensemanagement.base.ui.adapter.BasePagingAdapter
import com.android.pokhodai.expensemanagement.data.room.entities.WalletEntity
import com.android.pokhodai.expensemanagement.databinding.ItemWalletBinding
import com.android.pokhodai.expensemanagement.databinding.ItemWalletEmptyBinding
import com.android.pokhodai.expensemanagement.databinding.ItemWalletHeaderBinding
import com.android.pokhodai.expensemanagement.repositories.LanguageRepository
import com.android.pokhodai.expensemanagement.utils.*
import com.android.pokhodai.expensemanagement.utils.ClickUtils.setOnThrottleClickListener
import com.android.pokhodai.expensemanagement.utils.enums.Creater
import com.android.pokhodai.expensemanagement.utils.enums.Currency
import com.google.android.material.shape.CornerFamily
import javax.inject.Inject

class WalletAdapter @Inject constructor(
    private val languageRepository: LanguageRepository
) : BasePagingAdapter<WalletAdapter.ItemWallet>() {

    private val today = LocalDateFormatter.today()

    private val language = languageRepository.getLanguage()

    private var onLongClickActionListener: ((ActionWallet) -> Unit)? = null

    fun setOnLongClickActionListener(action: (ActionWallet) -> Unit) {
        onLongClickActionListener = action
    }

    private var onClickActionListener: ((View, String) -> Unit)? = null

    fun setOnClickActionListener(action: (View, String) -> Unit) {
        onClickActionListener = action
    }

    @SuppressLint("SetTextI18n")
    override fun build() {
        baseViewHolder(ItemWallet.WrapWallet::class, ItemWalletBinding::inflate) { item ->
            binding.run {
                ivAmount.setImageResource(item.wallet.icons.resId)
                txtNameAmount.text = item.wallet.categoryName
                txtTypeAmount.text = item.wallet.type
                txtAmount.text =
                    "${item.wallet.amount}${root.context.getString(item.wallet.currency.resId)}"
                txtAmount.setTextColor(
                    root.context.getColor(
                        if (item.wallet.type == root.context.getString(
                                R.string.status_income
                            )
                        ) R.color.green_600 else R.color.red_600
                    )
                )

                dividerBottomWallet.isVisible = !item.bottom

                val builderShapeModel = cardWallet.shapeAppearanceModel.toBuilder().apply {
                    val bottom = if (item.bottom) 8.dp else 0.dp
                    setBottomLeftCorner(CornerFamily.ROUNDED, bottom)
                    setBottomRightCorner(CornerFamily.ROUNDED, bottom)
                }
                cardWallet.shapeAppearanceModel = builderShapeModel.build()

                cardWallet.setOnLongClickListener {
                    txtAmount.context.showMenu(txtAmount) { creater ->
                        val action = if (creater == Creater.EDIT) {
                            ActionWallet.ActionEditWallet(item.wallet)
                        } else {
                            ActionWallet.ActionDeleteWallet(item.wallet.id ?: 0)
                        }
                        onLongClickActionListener?.invoke(action)
                    }
                    true
                }

                cardWallet.setOnThrottleClickListener {
                    item.wallet.description.takeIfNotEmpty()?.let {
                        onClickActionListener?.invoke(cardWallet, it)
                    }
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
                txtDateWallet.getTextDate(item.date, today, language)
                txtCountWallet.text = "${item.count}${root.context.getString(item.currency.resId)}"
            }
        }

        baseViewHolder(ItemWallet.EmptyWallet::class, ItemWalletEmptyBinding::inflate) { _ ->
            binding.txtWalletEmpty.text =
                binding.root.context.getString(R.string.home_item_wallet_empty)
        }
    }

    sealed class ActionWallet {
        data class ActionDeleteWallet(val id: Int) : ActionWallet()
        data class ActionEditWallet(val wallet: WalletEntity) : ActionWallet()
    }

    sealed class ItemWallet {
        data class WrapWallet(val wallet: WalletEntity, var bottom: Boolean = false) : ItemWallet()
        data class WrapHeader(
            val date: LocalDateFormatter,
            val count: String,
            val currency: Currency
        ) : ItemWallet()

        object EmptyWallet : ItemWallet()
    }
}