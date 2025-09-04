package com.sergei.pokhodai.expensemanagement.core.recycler.manager

import com.sergei.pokhodai.expensemanagement.core.recycler.RecyclerState

internal class RecyclerViewTypeManager {
    private val classToType = mutableMapOf<Class<RecyclerState>, Int>()
    private val typeToClass = mutableMapOf<Int, Class<RecyclerState>>()
    private var nextType = 0

    fun getType(item: RecyclerState): Int {
        return classToType.getOrPut(item.javaClass) {
            nextType++
            typeToClass[nextType] = item.javaClass
            nextType
        }
    }

    fun getClassByType(type: Int): Class<RecyclerState> {
        return typeToClass[type] ?: throw IllegalArgumentException("Unknown view type: $type")
    }
}