package com.sergei.pokhodai.expensemanagement.core.recycler.manager

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class RecyclerViewTypeManager {
    private val classToType = mutableMapOf<Class<out RecyclerState>, Int>()
    private val typeToClass = mutableMapOf<Int, Class<out RecyclerState>>()

    fun getType(item: RecyclerState): Int {
        return classToType.getOrPut(item.javaClass) {
            val newType = typeToClass.size
            typeToClass[newType] = item.javaClass
            newType
        }
    }

    fun getClassByType(type: Int): Class<out RecyclerState> {
        return typeToClass[type] ?: throw IllegalArgumentException("Unknown view type: $type")
    }
}