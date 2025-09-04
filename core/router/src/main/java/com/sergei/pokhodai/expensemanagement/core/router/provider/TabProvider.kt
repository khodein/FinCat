package com.sergei.pokhodai.expensemanagement.core.router.provider

import com.sergei.pokhodai.expensemanagement.core.router.destination.TabDestination

interface TabProvider {
    fun getDestination(): List<TabDestination>
}