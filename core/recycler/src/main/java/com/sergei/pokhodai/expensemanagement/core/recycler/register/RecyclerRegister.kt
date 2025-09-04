package com.sergei.pokhodai.expensemanagement.core.recycler.register

import android.content.Context
import android.view.View
import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState
import com.sergei.pokhodai.expensemanagement.core.recycler.factory.RecyclerTempFactory

class RecyclerRegister private constructor() {

    class Builder() {
        private var tempMap: HashMap<Class<out RecyclerState>, (context: Context) -> View>? = null

        fun add(clazz: Class<out RecyclerState>, onView: (Context) -> View): Builder {
            val registerMap = (tempMap ?: hashMapOf()).also {
                tempMap = it
            }
            return apply { registerMap[clazz] = onView }
        }

        fun build() {
            tempMap?.let(RecyclerTempFactory::register)
            tempMap = null
        }
    }
}