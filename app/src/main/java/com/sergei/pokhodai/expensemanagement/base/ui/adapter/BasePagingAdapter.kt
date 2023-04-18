package com.sergei.pokhodai.expensemanagement.base.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.sergei.pokhodai.expensemanagement.base.ui.fragments.getEqualsDiff
import kotlin.reflect.KClass

abstract class BasePagingAdapter<T : Any>(diffUtil: DiffUtil.ItemCallback<T> = getEqualsDiff()) :
    PagingDataAdapter<T, ViewHolder>(diffUtil) {

    abstract fun build()

    private val items = mutableListOf<Item<Any, ViewBinding>>()

    init {
        this.build()
    }

    final override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: throw Exception("Откуда тут взялся null???")
        val index = items.indexOfFirst {
            it.clazz == item::class
        }
        if (index == -1) throw Exception("${item.javaClass} не инициализирован")
        return index
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (val item = items[viewType]) {
            is Item.BaseItem -> {
                ViewHolder.BaseViewHolder(
                    item.vbInflater(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            is Item.CustomItem<Any, ViewBinding, *> -> {
                item.holder(
                    item.vbInflater(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: throw Exception("Откуда тут взялся null???")
        when (holder) {
            is ViewHolder.BaseViewHolder<*> -> {
                (items.first {
                    it.clazz == item::class
                } as Item.BaseItem<Any, ViewBinding>).bind.invoke(
                    holder as ViewHolder.BaseViewHolder<ViewBinding>,
                    item
                )
            }
            is ViewHolder.CustomViewHolder<*> -> {
                (holder as ViewHolder.CustomViewHolder<Any>).bind(item)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any, VB : ViewBinding> baseViewHolder(
        clazz: KClass<T>,
        vbInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
        bind: ViewHolder.BaseViewHolder<VB>.(T) -> Unit
    ) {
        val item = Item.BaseItem(clazz, vbInflater, bind)
        items += item as Item<Any, ViewBinding>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any, VB : ViewBinding, VH : ViewHolder> customViewHolder(
        clazz: KClass<T>,
        vbInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
        holder: (VB) -> VH
    ) {
        val item = Item.CustomItem(clazz, vbInflater, holder)
        items += item as Item<Any, ViewBinding>
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }
}