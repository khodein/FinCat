package com.sergei.pokhodai.expensemanagement.core.router.provider

import com.sergei.pokhodai.expensemanagement.core.router.destination.RouteDestination

interface RouteProvider {
    fun getDestination(): List<RouteDestination>
}