package com.sergei.pokhodai.expensemanagement.core.recycler.factory

import android.content.Context
import android.view.View
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal object RecyclerTempFactory {
    private val factory: HashMap<Class<out RecyclerState>, (context: Context) -> View> = hashMapOf()

    fun register(
        temp: HashMap<Class<out RecyclerState>, (context: Context) -> View>
    ) {
        factory.putAll(temp)
    }

    fun getTemp(clazz: Class<out RecyclerState>, context: Context): View {
        return factory[clazz]?.invoke(context) ?: throw IllegalArgumentException("Unknown state class: $clazz")
    }
}