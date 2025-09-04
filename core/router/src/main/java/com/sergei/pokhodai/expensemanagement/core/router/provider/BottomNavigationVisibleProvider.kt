package com.sergei.pokhodai.expensemanagement.core.router.provider

interface BottomNavigationVisibleProvider {
    fun getRouteListIsNotVisible(): List<Int>
}